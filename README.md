# Java Modernization Labs

A collection of small, focused Java modernization exercises.  
Each **day** builds on modern Java practices, tools, and APIs, progressing from core syntax to advanced patterns and frameworks.

---

## Purpose
This repository is designed for Java developers looking to modernize their coding practices.  
It covers updated Java syntax, APIs, libraries, and design patterns through small, focused exercises.  
The goal is to progressively build from fundamental concepts to advanced techniques.

---

## 📅 Labs Overview

| Day  | Module Name                        | Description |
|------|------------------------------------|-------------|
| 0    | [Environment Setup](README.md)     | Setting up Java 17, Maven, IntelliJ, and Git. |
| 1    | [Immutable Config Loader](day1-immutable-config-loader) | Load `config.properties` into an immutable map with optional secret masking and JSON export. |
| 2+   | *(Coming soon)*                    | TBD |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- IntelliJ IDEA (recommended)

### Clone the Repository
```bash
git clone https://github.com/SilentKB3/java-modernization-labs.git
cd java-modernization-labs
```

---

## Running the Labs
Each lab is a self-contained Maven module
Example - running the Day 1 Immutable Config Loader:

```bash
cd day1
mvn compile exec:java \
  -Dexec.mainClass="com.example.configloader.ConfigLoader" \
  -Dexec.args="--mask"
```
You can also run from IntelliJ by selecting the module and setting program arguments in Run Configurations

## Structure
java-modernization-labs/
├── day0/   # Environment setup notes & scripts
├── day1/   # Immutable Config Loader
│   ├── src/
│   ├── pom.xml
│   └── README.md
└── README.md  # This file


## Modules
[Day 1 – Immutable Config Loader](day1-immutable-config-loader)

## License
This project is licensed under the MIT License.

