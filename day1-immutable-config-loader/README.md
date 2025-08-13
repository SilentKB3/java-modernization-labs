# Day 1 – Immutable Config Loader

This module reads a `config.properties` file from the `src/main/resources` folder,  
loads it into an **immutable map**, and supports **optional masking of secrets** and **JSON export**.

---

## ✨ Features
- **Immutable map** – configuration is read-only after loading.
- **Optional masking** – hide secret values (passwords, tokens, keys) with `--mask`.
- **JSON export** – export configuration in JSON format with `--json`.
- **Combined flags** – `--mask` and `--json` can be used together in any order.
- **Unit tests** – core features and edge cases tested with **JUnit 5**.

---

## 📦 Requirements
- **Java 17+** (primary target)
- **Maven 3.6+**
- IntelliJ IDEA (recommended)

---

## Project Structure
day1-immutable-config-loader/
├── src/
│ ├── main/
│ │ ├── java/com/example/configloader/
│ │ │ └── ConfigLoader.java
│ │ └── resources/
│ │ └── config.properties
│ └── test/
│ └── java/com/example/configloader/
│ └── ConfigLoaderTest.java
├── pom.xml
└── README.md

---

## Usage 

### 1. Compile & Package
```bash
mvn -q clean package
```

Run Normally:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar \
  com.example.configloader.ConfigLoader```
```

Run with Masking Secrets:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar \
  com.example.configloader.ConfigLoader --mask
```

Run with JSON exports:
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar \
  com.example.configloader.ConfigLoader --json
```

5. Run with Both JSON & Masking
```bash
java -cp target/day1-immutable-config-loader-1.0.0.jar \
  com.example.configloader.ConfigLoader --json --mask
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
== JSON Export ==
{
  "db.username": "admin",
  "db.password": "supersecret",
  "api.key": "123456",
  "timeout": "30"
}


---

## Author
Kenneth Baity
