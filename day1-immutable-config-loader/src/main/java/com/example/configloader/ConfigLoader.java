package com.example.configloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ConfigLoader {
    public static void main(String[] args) {
        // Accept --mask anywhere in args (not only length==1)
        boolean mask = Arrays.stream(args).anyMatch("--mask"::equals);
        boolean json = Arrays.stream(args).anyMatch("--json"::equals);

        final String resourceName = "config.properties";

        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                System.err.println("ERROR: Missing " + resourceName + " in src/main/resources");
                System.exit(1);
            }

            Properties props = new Properties();
            props.load(in);

            // Keep the config values as-is (immutable map)
            Map<String, String> config = toImmutableMap(props);
            if (json) {
                printAsJson(config, mask);
            } else {
                System.out.println("== Loaded configuration ==");
                for (Map.Entry<String, String> e : config.entrySet()) {
                    String key = e.getKey();
                    String value = e.getValue();
                    // Mask only if flag ON *and* key looks secret
                    String shown = (mask && isSecretKey(key)) ? maskValue(value) : value;
                    System.out.println(key + "=" + shown);
                }
            }
        } catch (IOException e) {
            System.err.println("ERROR: Failed to load properties: " + e.getMessage());
            System.exit(2);
        }
    }

    private static void printAsJson(Map<String, String> config, boolean mask) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        int i = 0, n = config.size();
        for (Map.Entry<String, String> e : config.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();
            String shown = (mask && isSecretKey(key)) ? maskValue(value) : value;

            sb.append("  \"")
                    .append(jsonEscape(key))
                    .append("\": \"")
                    .append(jsonEscape(shown))
                    .append("\"");

            if (++i < n) sb.append(",");
            sb.append("\n");
        }
        sb.append("}");
        System.out.println("== JSON Export ==\n" + sb);
    }

    private static String jsonEscape(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\b' -> out.append("\\b");
                case '\f' -> out.append("\\f");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default   -> {
                    if (c < 0x20) out.append(String.format("\\u%04x", (int)c));
                    else out.append(c);
                }
            }
        }
        return out.toString();
    }



    // v1: simple rules; tweak anytime
    private static boolean isSecretKey(String key) {
        String k = key.toLowerCase();
        return k.contains("password") || k.contains("secret") || k.contains("token") || k.endsWith("key");
    }

    // Fixed-length mask (******) or length-matched mask—pick one:
    private static String maskValue(String original) {
        // return "******";             // fixed (simple)
        return "*".repeat(original.length()); // length-matched (what you did)
    }

    // No-lambda version: Properties -> immutable Map<String,String>
    private static Map<String, String> toImmutableMap(Properties props) {
        Map<String, String> mutable = new LinkedHashMap<>();
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            mutable.put(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
        }
        return Map.copyOf(mutable); // unmodifiable snapshot

        //return props.entrySet().stream()
        //        .collect(Collectors.toUnmodifiableMap(
        //            e -> String.valueOf(e.getKey()),
        //            e -> String.valueOf(e.getValue())
        //        ));
    }
}
