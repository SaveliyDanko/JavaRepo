# Реализация — прогресс
Источник плана: docs/pipeline/stages/   •   Обновлено: 2026-06-18 / сессия T10

## Где мы сейчас (resume here)
- Текущий stage: 04 — `ready-for-review` (все 3 под-шага `done`). Это последний stage темы.
- Текущий шаг: stage 04 закрыт воркером; ждёт ревью (роль 04).
- Статус шага: инвариант «один активный stage» соблюдён (01–03 approved, 04 ready-for-review).
- Ретраи текущего шага: 0
- Следующее действие: REVIEW stage 04 (роль 04) — сверить README с планом/гайдом, прогнать финальный `./gradlew test`, вынести вердикт.
- Состояние проверок: финальный `./gradlew test --rerun-tasks` зелёный — 43 теста (42 параметризованных кейса + MainTest), 0 failures/errors. Scope чист: в рабочем дереве изменён только `README.md`, `build.gradle.kts` не тронут.

## Stages
| #  | Stage                          | Статус реализации | Готово шагов | Файл плана                    |
|----|--------------------------------|-------------------|--------------|-------------------------------|
| 01 | SUT `NumberClassifier`         | approved          | 3/3          | 01-sut-number-classifier.md   |
| 02 | Параметризованные тесты        | approved          | 5/5          | 02-parameterized-tests.md     |
| 03 | Сводка `parameterized-tests.md`| approved          | 4/4          | 03-theory-summary.md          |
| 04 | README ветки + DoD             | ready-for-review  | 3/3          | 04-readme-and-dod.md          |

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

### Stage 04 — README ветки + финальный DoD  [ready-for-review]
- [x] 4.1 Обновить README ветки под тему (раздел темы + структура пакета + ссылка на сводку)
- [x] 4.2 Финальный прогон `./gradlew test`
- [x] 4.3 Сверка проектного DoD темы

## Журнал
- 1.1 Проверка-предпосылка — done. Сделано: активна ветка `parameterized-tests`; baseline `./gradlew test` зелёный (только `MainTest`). Отклонение от среды: `JAVA_HOME` не в PATH — использован JDK из SDKMAN (`~/.sdkman/candidates/java/current`, Temurin 21.0.10). `build.gradle.kts` не менялся.
- 1.2 enum `Category` — done. Сделано: пакет `com.savadanko.javarepo.parameterizedtests`, enum `Category{NEGATIVE,ZERO,ONE,PRIME,COMPOSITE}` + `isStandard()` (true для PRIME/COMPOSITE). Файлы: `src/main/java/com/savadanko/javarepo/parameterizedtests/Category.java`.
- 1.3 `NumberClassifier` — done. Сделано: `isEven`/`isOdd`/`isPrime` (контракт `n<2`→false, делители до `d*d<=n` в long), `classify(int)`→`Category`, `isParsableNumber(String)` (null/пусто/пробелы→false). Учебные javadoc-комментарии по стилю темы. Файлы: `src/main/java/com/savadanko/javarepo/parameterizedtests/NumberClassifier.java`. Отклонений от плана нет.
- 2.1–2.5 Параметризованные тесты — done (реализованы одним файлом, единый тест-класс по решению архитектора). Сделано: `NumberClassifierParameterizedTest` со всеми источниками плана — `@ValueSource`, `@MethodSource` (фабрики `primes`/`nonPrimes` → `IntStream`), `@CsvSource` (встроенная конверсия в `int`/`Category`), `@EnumSource` (полный перебор + `names`/`mode=EXCLUDE`), `@NullSource`/`@EmptySource`/`@NullAndEmptySource` (комбинирование источников). У каждого `@ParameterizedTest` — атрибут `name`, перед каждой группой — поясняющий комментарий. Проверка: `./gradlew test` зелёный, 42 кейса, 0 failures, детерминированы (без сети/ресурсов). Файлы: `src/test/java/com/savadanko/javarepo/parameterizedtests/NumberClassifierParameterizedTest.java`. Отклонений от плана нет; объём не расширялся (`@CsvFileSource`/кастомные провайдеры оставлены сводке stage 03).
- 3.1–3.4 Сводка темы — done. Сделано: `docs/parameterized-tests.md` в формате `how-to-add-a-topic.md` — «Что это и зачем», «Ключевые понятия» (все 6 источников: `@ParameterizedTest`+`name`, `@ValueSource`, `@MethodSource`, `@CsvSource`, `@EnumSource`, `@NullSource`/`@EmptySource`/`@NullAndEmptySource`), «Абстракции в коде» (каждый источник связан с конкретным методом `NumberClassifierParameterizedTest` и контрактом `NumberClassifier`/`Category`), «Полезно знать» (несколько источников на метод, `./gradlew test`, расширения `@CsvFileSource`/кастомные провайдеры помечены вне v1). Имена аннотаций/методов сверены с реальным кодом. Только `docs/` — код/сборку/тесты не меняет. Отклонений от плана нет.
- 4.1 README ветки — done. Сделано: `README.md` ветки `parameterized-tests` переписан по образцу эталона `jms` — шапка темы, примечание «доп. зависимостей не нужно (`junit-jupiter-params` транзитивно), `build.gradle.kts` не менялся», стек, блок «Что внутри» с реальной структурой пакета (`Category`/`NumberClassifier` в `src/main`, `NumberClassifierParameterizedTest` в `src/test`), раздел «Теория» со ссылкой `[docs/parameterized-tests.md]` (теория НЕ продублирована — правило гайда), команда `./gradlew test`, «Дальше можно попробовать» (расширения). Имена пакета/классов сверены с `find` по реальным файлам. Только `README.md`. Отклонений от плана нет.
- 4.2 Финальный прогон — done. `./gradlew test --rerun-tasks` зелёный: 43 теста (42 параметризованных кейса в `NumberClassifierParameterizedTest` + `MainTest`), 0 failures, 0 errors, детерминированы (без сети/ресурсов). JDK: Temurin 21 из SDKMAN (`JAVA_HOME`).
- 4.3 Сверка проектного DoD — done. Все пункты зелёные: SUT `NumberClassifier`+`Category`+`isParsableNumber` (stage 01) ✓; все источники параметров продемонстрированы (stage 02) ✓; сводка `docs/parameterized-tests.md` на месте и связана с кодом (stage 03) ✓; README ветки обновлён и ссылается на сводку (этот stage) ✓; `./gradlew test` зелёный ✓. Чек-лист `how-to-add-a-topic.md`: тема в ветке `parameterized-tests` (не `main`), код с комментариями, тест проходит, сводка и README на месте — выполнен. Scope чист: в рабочем дереве изменён только `README.md`, `build.gradle.kts` не тронут.
