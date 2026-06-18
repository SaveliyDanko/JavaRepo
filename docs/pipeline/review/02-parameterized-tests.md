# Ревью stage 02 — Параметризованные тесты по источникам
План: docs/pipeline/stages/02-parameterized-tests.md   •   Раунд: 1   •   Вердикт: approved
Итог: blocking — 0 open / 0 verified;  nit — 0 open

## Сверка заявленного с реальностью
- Заявлено `ready-for-review`, 5/5 под-шагов `done` — подтверждено по коду.
- `./gradlew test --rerun-tasks` зелёный: `NumberClassifierParameterizedTest` — 42 кейса, 0 failures / 0 errors; baseline `MainTest` — 1, 0 failures. Детерминированы, без сети/ресурсов.
- Scope чист: diff vs `main` затрагивает только тему (`src/main/.../Category.java`, `NumberClassifier.java` — из stage 01; `src/test/.../NumberClassifierParameterizedTest.java` — этот stage) и `docs/pipeline/`. `build.gradle.kts` не тронут (`junit-jupiter-params` транзитивен).

## Соответствие плану и DoD
- [x] Класс `NumberClassifierParameterizedTest` в пакете темы (`src/test/.../parameterizedtests`).
- [x] Все источники продемонстрированы: `@ValueSource` (even/odd), `@MethodSource` (`primes`/`nonPrimes` → `IntStream`), `@CsvSource` (встроенная конверсия `String→int` и `String→Category`), `@EnumSource` (полный перебор + `names` + `mode = EXCLUDE`), `@NullSource`/`@EmptySource`/`@NullAndEmptySource` (комбинирование источников).
- [x] У каждого `@ParameterizedTest` задан читаемый `name`; перед каждой группой — поясняющий комментарий «что показывает источник».
- [x] `./gradlew test` зелёный; кейсы детерминированы.
- Согласованность тест-данных с контрактом SUT проверена: `nonPrimes {0,1,4,9,-3}` и `@CsvSource` (`2→PRIME, 4→COMPOSITE, 0→ZERO, 1→ONE, -3→NEGATIVE`) соответствуют `isPrime`/`classify`; инвариант `isStandard()` согласован для всех констант `Category`.
- Дисциплина объёма соблюдена: `@CsvFileSource` и кастомные провайдеры не добавлены (оставлены сводке stage 03), как требует план.

## Замечания
- (нет)

## Возврат к архитектору (если есть)
- (нет)
