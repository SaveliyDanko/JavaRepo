# JavaRepo

Учебный репозиторий для изучения и практики Java.

`main` держится максимально чистым: только Java + Gradle + JUnit 5.
Любые зависимости (Spring, JPA, БД и т.п.) подключаются в отдельных
тематических ветках под конкретную тему изучения.

## Стек

- **Java 21** (LTS)
- **Gradle** (Kotlin DSL, `build.gradle.kts`) + wrapper
- **JUnit 5** — тесты

## Структура

```
src/main/java/com/savadanko/javarepo/Main.java
src/test/java/com/savadanko/javarepo/MainTest.java
```

## Команды

```bash
./gradlew build   # собрать и прогнать тесты
./gradlew test    # только тесты
./gradlew run     # запустить Main (требует плагина application — добавишь при необходимости)
```

> Для `./gradlew run` понадобится плагин `application` в `build.gradle.kts`.
> Пока запускать можно из IDE или собранным classpath.
