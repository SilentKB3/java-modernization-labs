# Day 1 — Immutable Config Loader

This module reads a `config.properties` file from the `src/main/resources` folder, loads it into an **immutable map**, and supports optional masking of secrets and JSON export.

---

## Features
- **Immutable map** - loaded configuration
- **Optional masking** - mask secret values (passwords, tokens, keys) with `--mask`.
- **JSON export** - export configuration as JSON with `--json`.
- **Combined flags** - `--mask` and `--json` can be used together in any order
- **Unit tests** - verifies core features and edge cases using JUnit 5
  
---

## Requirements
- JDK 17 (primary target)
- Maven 3.6
- IntelliJ IDEA (recommended)

---

## Project Structure
day1-immutable-config-loader
  src
    main
      java/com/example/configloader/
        ConfigLoader.java
      resources
        confg.properties
    test
      java/com/example/configLoader/
        ConfigLoaderTest.javaa
  pom.xml
  README.md

---

## Usage 

### 1. Compile & Package
```bash
mvn -q clean package
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader
```

Run Normally:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader
```

Run with masking secrets:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader --mask
```

Run to export as JSON:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader --json
```

Run (with JSON and masking):
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar com.example.configloader.ConfigLoader --json --mask
```

Run Tests
```bash
mvn test
```

---

## Example config.properties

db.username=admin
db.password=supersecret
api.key=123456
timeout=30

---

## Sample Output

### Normal Run

== Loaded configuration ==
db.username=admin
db.password=supersecret
api.key=123456
timeout=30

### With Mask
== Loaded configuration ==
db.username=admin
db.password=***********
api.key=******
timeout=30

### With JSON
{
  "db.username": "admin",
  "db.password": "supersecret",
  "api.key": "123456",
  "timeout": "30"
}

---

## Author
Kenneth Baity
