# java-modernization-labs
A collection of hands-on Java projects exploring new features from Java 9 to Java 21, along with concurrency, functional programming, and design principles

## Structure
- Each sprint is in its own folder, e.g., `day1-config-loader/`
  - `src/` — Source files
  - `resources/` — Supporting data (e.g., `.properties`)
  - `README.md` — Sprint-specific documentation

## Goals
- Become fluent with modern Java (post–Java 8)
- Build concurrency, functional programming, and OO design skills
- Create GitHub-ready artifacts for your portfolio

## Tools
- Java 17 (primary target)
- IntelliJ IDEA
- Maven or Gradle (as desired)

## License
MIT

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

