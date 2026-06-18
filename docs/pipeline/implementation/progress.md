# Реализация — прогресс
Источник плана: docs/pipeline/stages/   •   Обновлено: 2026-06-18 / сессия T9

## Где мы сейчас (resume here)
- Текущий stage: 03 — `approved` (ревью раунд 1, 0 blocking / 0 nit). Следующий — IMPLEMENT stage 04 (README ветки + DoD).
- Текущий шаг: stage 03 закрыт; stage 04 ещё `todo` — следующий незакрытый stage по плану.
- Статус шага: инвариант «один активный stage» соблюдён (01–03 approved, 04 todo).
- Ретраи текущего шага: 0
- Следующее действие: воркер (роль 03) реализует stage 04 — обновить README ветки под тему со ссылкой на `docs/parameterized-tests.md`, прогнать финальный `./gradlew test`.
- Состояние проверок: stage 03 — документация (только `docs/`), кода/сборки не меняет; имена аннотаций и методов в сводке сверены с `NumberClassifierParameterizedTest`/`NumberClassifier`/`Category` (ревью `review/03-theory-summary.md`). Тесты прежние — зелёные (T7).

## Stages
| #  | Stage                          | Статус реализации | Готово шагов | Файл плана                    |
|----|--------------------------------|-------------------|--------------|-------------------------------|
| 01 | SUT `NumberClassifier`         | approved          | 3/3          | 01-sut-number-classifier.md   |
| 02 | Параметризованные тесты        | approved          | 5/5          | 02-parameterized-tests.md     |
| 03 | Сводка `parameterized-tests.md`| approved          | 4/4          | 03-theory-summary.md          |
| 04 | README ветки + DoD             | todo              | 0/?          | 04-readme-and-dod.md          |

## Шаги
### Stage 01 — SUT `NumberClassifier` + `Category`  [approved]
- [x] 1.1 Проверка-предпосылка (ветка `parameterized-tests` + baseline `./gradlew test` зелёный)
- [x] 1.2 Пакет темы + enum `Category`
- [x] 1.3 `NumberClassifier` (предикаты + `classify` + `isParsableNumber`)

### Stage 02 — Параметризованные тесты  [approved]
- [x] 2.1 `@ValueSource` — `evenNumbers` / `oddNumbers`
- [x] 2.2 `@MethodSource` — `primeNumbers` / `notPrime` (+ фабрики `primes`/`nonPrimes`)
- [x] 2.3 `@CsvSource` — `classifyFromCsv` (конверсия `String→int`, `String→Category`)
- [x] 2.4 `@EnumSource` — `everyCategoryIsConsistent` / `standardCategories` / `specialCategories` (`names`+`mode`)
- [x] 2.5 Null/Empty — `blankIsNotParsable` (`@NullSource`+`@EmptySource`) / `nullAndEmptyCombined` (`@NullAndEmptySource`+`@ValueSource`)

### Stage 03 — Сводка `docs/parameterized-tests.md`  [approved]
- [x] 3.1 Заголовок + «Что это и зачем»
- [x] 3.2 Ключевые понятия (все 6 источников)
- [x] 3.3 Связь источников с методами в коде
- [x] 3.4 «Полезно знать» (расширения помечены вне v1)

## Журнал
- 1.1 Проверка-предпосылка — done. Сделано: активна ветка `parameterized-tests`; baseline `./gradlew test` зелёный (только `MainTest`). Отклонение от среды: `JAVA_HOME` не в PATH — использован JDK из SDKMAN (`~/.sdkman/candidates/java/current`, Temurin 21.0.10). `build.gradle.kts` не менялся.
- 1.2 enum `Category` — done. Сделано: пакет `com.savadanko.javarepo.parameterizedtests`, enum `Category{NEGATIVE,ZERO,ONE,PRIME,COMPOSITE}` + `isStandard()` (true для PRIME/COMPOSITE). Файлы: `src/main/java/com/savadanko/javarepo/parameterizedtests/Category.java`.
- 1.3 `NumberClassifier` — done. Сделано: `isEven`/`isOdd`/`isPrime` (контракт `n<2`→false, делители до `d*d<=n` в long), `classify(int)`→`Category`, `isParsableNumber(String)` (null/пусто/пробелы→false). Учебные javadoc-комментарии по стилю темы. Файлы: `src/main/java/com/savadanko/javarepo/parameterizedtests/NumberClassifier.java`. Отклонений от плана нет.
- 2.1–2.5 Параметризованные тесты — done (реализованы одним файлом, единый тест-класс по решению архитектора). Сделано: `NumberClassifierParameterizedTest` со всеми источниками плана — `@ValueSource`, `@MethodSource` (фабрики `primes`/`nonPrimes` → `IntStream`), `@CsvSource` (встроенная конверсия в `int`/`Category`), `@EnumSource` (полный перебор + `names`/`mode=EXCLUDE`), `@NullSource`/`@EmptySource`/`@NullAndEmptySource` (комбинирование источников). У каждого `@ParameterizedTest` — атрибут `name`, перед каждой группой — поясняющий комментарий. Проверка: `./gradlew test` зелёный, 42 кейса, 0 failures, детерминированы (без сети/ресурсов). Файлы: `src/test/java/com/savadanko/javarepo/parameterizedtests/NumberClassifierParameterizedTest.java`. Отклонений от плана нет; объём не расширялся (`@CsvFileSource`/кастомные провайдеры оставлены сводке stage 03).
- 3.1–3.4 Сводка темы — done. Сделано: `docs/parameterized-tests.md` в формате `how-to-add-a-topic.md` — «Что это и зачем», «Ключевые понятия» (все 6 источников: `@ParameterizedTest`+`name`, `@ValueSource`, `@MethodSource`, `@CsvSource`, `@EnumSource`, `@NullSource`/`@EmptySource`/`@NullAndEmptySource`), «Абстракции в коде» (каждый источник связан с конкретным методом `NumberClassifierParameterizedTest` и контрактом `NumberClassifier`/`Category`), «Полезно знать» (несколько источников на метод, `./gradlew test`, расширения `@CsvFileSource`/кастомные провайдеры помечены вне v1). Имена аннотаций/методов сверены с реальным кодом. Только `docs/` — код/сборку/тесты не меняет. Отклонений от плана нет.
