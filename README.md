# JavaRepo — ветка `parameterized-tests`

Тема: **@Parameterized Tests (JUnit 5)** — один и тот же тест прогоняется на
множестве наборов входных данных.

> Эта ветка содержит пример параметризованных тестов. Чистый каркас живёт в `main`.
> Дополнительных зависимостей не нужно: `junit-jupiter-params` приходит
> транзитивно вместе с `junit-jupiter`, `build.gradle.kts` не менялся.

## Стек

- **Java 21**, **Gradle** (Kotlin DSL)
- **JUnit 5** (`junit-jupiter`) — параметризованные тесты через
  `junit-jupiter-params` (транзитивная зависимость)

## Что внутри

Один маленький SUT и один тест-класс, в котором по очереди показаны все
источники параметров JUnit 5:

```
src/main/java/com/savadanko/javarepo/parameterizedtests/
├── Category.java          # enum-категория числа (для @EnumSource)
└── NumberClassifier.java  # классификатор: isEven/isOdd/isPrime/classify/isParsableNumber
src/test/java/com/savadanko/javarepo/parameterizedtests/
└── NumberClassifierParameterizedTest.java  # @ValueSource, @MethodSource, @CsvSource,
                                            # @EnumSource, @NullSource/@EmptySource
```

## Теория

Краткая теоретическая сводка по теме — в [docs/parameterized-tests.md](docs/parameterized-tests.md).

## Команды

```bash
./gradlew test    # прогнать параметризованные тесты
```

## Дальше можно попробовать

- `@CsvFileSource` — наборы данных из CSV-файла в ресурсах
- Кастомный `ArgumentsProvider` через `@ArgumentsSource`
- Конвертеры/агрегаторы аргументов (`@ConvertWith`, `@AggregateWith`)
- `@ParameterizedTest` с несколькими источниками сразу и фильтрами `@EnumSource(names=…, mode=…)`
