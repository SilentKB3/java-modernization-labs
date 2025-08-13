# Day 1 — Immutable Config Loader

Load a `.properties` file into an **immutable** `Map<String, String>` using Java 9+ factory methods and print the configuration safely.

## What you’ll see
- Java 10 `var` for local inference
- Java 9 immutable collections: `Map.ofEntries(...)`
- Graceful error handling for missing/invalid files

## Requirements
- JDK 17 (primary target), JDK 21 optional
- Maven or Gradle
- IntelliJ IDEA

## Run (Maven)
```bash
mvn -q clean package
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader
```

## Usage 
Run (no args):
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader
```

Run with masking:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader --mask
```

Run (with Json):
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader --json
```

Run (with Json and masking):
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader --json --mask
```
