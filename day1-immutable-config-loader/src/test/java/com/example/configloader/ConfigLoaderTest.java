package com.example.configloader;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    // ---------- helpers to call private methods (keep your code private) ----------
    private static boolean callIsSecretKey(String key) throws Exception {
        Method m = ConfigLoader.class.getDeclaredMethod("isSecretKey", String.class);
        m.setAccessible(true);
        return (boolean) m.invoke(null, key);
    }

    private static String callMaskValue(String v) throws Exception {
        Method m = ConfigLoader.class.getDeclaredMethod("maskValue", String.class);
        m.setAccessible(true);
        return (String) m.invoke(null, v);
    }

    // ---------- tiny util to capture System.out for a runnable ----------
    private static String captureStdout(Runnable r) {
        PrintStream original = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        try {
            r.run();
            return baos.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(original);
        }
    }

    private static String normalize(String s) {
        // avoid CRLF/CR differences across OSes for simple assertions
        return s.replace("\r\n", "\n").replace("\r", "\n");
    }

    // ---------- integration-style tests against main() ----------
    @Test
    void configLoader_printsMaskedValues_whenMaskFlagProvided() {
        String out = captureStdout(() -> ConfigLoader.main(new String[]{"--mask"}));
        out = normalize(out);
        assertTrue(out.contains("== Loaded configuration =="));

        // Adjust keys to match your config.properties
        assertTrue(out.contains("db.password=" + "******"), "password should be masked");
        assertFalse(out.contains("db.password=secret"), "raw password should not appear");

        // sanity check: non-secret key still shows value
        assertTrue(out.contains("app.env=dev") || out.matches("(?s).*app\\.env=\\w+.*"),
                "non-secret values should print unmasked");
    }

    @Test
    void configLoader_printsJson_whenJsonFlagProvided() {
        String out = captureStdout(() -> ConfigLoader.main(new String[]{"--json"}));
        out = normalize(out);
        assertTrue(out.contains("== JSON Export =="));
        assertTrue(out.contains("{"));
        assertTrue(out.contains("}"));
        // basic shape: "configKey": "value" (don’t over-fit)
        assertTrue(out.matches("(?s).*\"db\\.url\"\\s*:\\s*\".*\".*"));
    }

    @Test
    void configLoader_printsMaskedJson_whenJsonAndMaskFlagsProvided() {
        String out = captureStdout(() -> ConfigLoader.main(new String[]{"--json", "--mask"}));
        out = normalize(out);
        assertTrue(out.contains("== JSON Export =="));
        assertTrue(out.contains("******"), "masked token should appear");
        // ensure raw secret isn't present
        assertFalse(out.contains("\"db.password\": \"secret\""));
    }

    // ---------- unit-ish tests for private helpers via reflection ----------
    @Test
    void isSecretKey_detectsCommonSecrets_caseInsensitive() throws Exception {
        assertTrue(callIsSecretKey("db.password"));
        assertTrue(callIsSecretKey("API.TOKEN"));
        assertTrue(callIsSecretKey("serviceApiKey"));
        assertTrue(callIsSecretKey("my-secret"));
        assertFalse(callIsSecretKey("username"));
        assertFalse(callIsSecretKey("user-id"));   // not ending with "key", not secret-like
    }

    @Test
    void maskValue_matchesLength() throws Exception {
        assertEquals("******", callMaskValue("secret"));
        assertEquals("***", callMaskValue("abc"));
        assertEquals("", callMaskValue(""));
    }
}
