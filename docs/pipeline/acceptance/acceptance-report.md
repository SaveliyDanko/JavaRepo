# Приёмка проекта
Эталон: docs/pipeline/design/01-requirements.md   •   Раунд: 1   •   Вердикт: accepted

Тема: «@Parameterized Tests» (JUnit 5). Приёмка проведена приёмщиком (роль 05)
read-only по коду; вход — гейт GT4 (все stages `approved` + проект собирается).

## Проектный DoD
- [x] Сборка проходит — `./gradlew clean test --rerun-tasks` → BUILD SUCCESSFUL.
- [x] Все тесты зелёные — 43 теста (42 параметризованных кейса в
  `NumberClassifierParameterizedTest` + `MainTest`), 0 failures / 0 errors / 0 skipped.
- [x] Сквозной сценарий темы работает — для учебной темы e2e = примеры реально
  прогоняются: `./gradlew test` демонстрирует все источники параметров зелёными,
  детерминированно (без сети/внешних ресурсов).
- [x] Все требования из `01-requirements.md` покрыты (см. ниже).
- [x] Нет открытых эскалаций и blocking-findings — `escalations.md` отсутствует
  (0 открытых); все 4 stages `approved` с 0 blocking в ревью.
- [x] Базовая документация запуска присутствует — `README.md` ветки (раздел темы,
  структура пакета, команда `./gradlew test`) + сводка `docs/parameterized-tests.md`.
- [x] Соответствие архитектуре `02-architecture.md` — один SUT-класс + один
  тест-класс по источникам, `build.gradle.kts` не тронут (diff vs `main` пуст).

## Покрытие требований
Эталон — `docs/design/01-requirements.md` (объём v1).

- **Критерий успеха** «`./gradlew test` зелёный» — покрыто (clean-прогон зелёный, 0 failures).
- **Критерий успеха** «примеры демонстрируют ключевые источники» — покрыто
  (тест `NumberClassifierParameterizedTest`, 11 `@ParameterizedTest`, 42 кейса).
- **Критерий успеха** «есть `docs/parameterized-tests.md`» — покрыто (файл на месте, 4369 б).
- Ветка `parameterized-tests` от чистого `main` — покрыто (работаем в ветке; vs `main`
  изменён только `README.md`, остальное — новые файлы темы и pipeline-артефакты).
- SUT `NumberClassifier` (even/odd/prime) в `src/main` — покрыто
  (`NumberClassifier.isEven/isOdd/isPrime/classify`, `Category{NEGATIVE,ZERO,ONE,PRIME,COMPOSITE}`).
- Источник `@ValueSource` — покрыто (тест, 4 применения; even/odd).
- Источник `@MethodSource` — покрыто (тест, 3 применения; фабрики `primes`/`nonPrimes`).
- Источник `@CsvSource` — покрыто (тест, 2 применения; конверсия `String→int`/`String→Category`).
- Источник `@EnumSource` — покрыто (тест, 4 применения; `names`/`mode`).
- Источники `@NullSource`/`@EmptySource`/`@NullAndEmptySource` — покрыто
  (по 1 применению; на `isParsableNumber(String)`).
- `docs/parameterized-tests.md` в формате `how-to-add-a-topic.md` — покрыто (stage 03, ревью approved).
- README ветки обновлён, ссылка на сводку — покрыто (`README.md:32` ссылается на сводку).
- Нефункциональные (детерминизм, без сети/ресурсов, читаемые имена кейсов через `name`) —
  покрыто (атрибут `name` у каждого `@ParameterizedTest`; источники инлайн/в коде).
- Тех. ограничения (Java 21, Gradle Kotlin DSL, JUnit 5; без отдельной зависимости и
  без плагина `application`) — соблюдено: в `build.gradle.kts` нет ни `application`,
  ни явного `junit-jupiter-params` (транзитивно через `junit-jupiter`).

### Вне объёма v1 — корректно НЕ реализовано (проверено отсутствие)
- `@CsvFileSource` — 0 вхождений в `src/`.
- Кастомный `ArgumentsProvider`/`ArgumentConverter`/`ArgumentsAggregator` — 0 в `src/`.
- Запускаемый Demo/`main` темы и плагин `application` — не добавлялись.
Эти пункты описаны как расширения в сводке и README («Дальше можно попробовать»).

## Замечания и возвраты
Нет. 0 blocking, 0 nit; возвратов в реализацию (роль 03) и эскалаций к
дизайну/архитектуре (роли 01/02) не требуется.

## Вердикт
**accepted** — проект готов (release-ready по содержанию). Все пункты DoD зелёные,
все требования v1 покрыты, объём не расширен. Гейт GR6 (вердикт приёмки,
auto-with-guard) пройден: guard выполнен (проектный DoD зелёный — сборка, все
тесты, сквозной прогон примеров).

> Релиз/деплой/merge в `main` — отдельное необратимое действие и выполняется
> только по явному подтверждению человека (Общие правила), даже при `accepted`.
