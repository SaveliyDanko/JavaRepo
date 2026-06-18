# Stages — тема «@Parameterized Tests» (JUnit 5)

Обновлено: 2026-06-18
Основано на: requirements 2026-06-18, architecture 2026-06-18

**Готовность к разработке:** да — детализация завершена (2026-06-18)

Индекс всех stages. Детали — в `NN-<slug>.md`. Реализация строго
последовательная: один незакрытый stage за раз.

| #  | Stage                          | Цель (1 строка)                                                        | Зависит от | Статус детализации | Файл                          |
|----|--------------------------------|-----------------------------------------------------------------------|------------|--------------------|-------------------------------|
| 01 | SUT `NumberClassifier`         | Класс even/odd/prime + `Category` + `isParsableNumber(String)`        | —          | done               | 01-sut-number-classifier.md   |
| 02 | Параметризованные тесты        | Один тест-класс: `@ValueSource`/`@MethodSource`/`@CsvSource`/`@EnumSource`/Null-Empty | 01         | done               | 02-parameterized-tests.md     |
| 03 | Сводка `docs/parameterized-tests.md` | Теория темы в формате `how-to-add-a-topic.md`, связать с кодом   | 01, 02     | done               | 03-theory-summary.md          |
| 04 | README ветки + DoD             | README под тему + ссылка на сводку; финальный `./gradlew test` зелёный | 02, 03     | done               | 04-readme-and-dod.md          |

Статусы детализации: `todo` → `in-progress` → `done`.
Флаг «Готовность к разработке» держим `нет`, пока не детализированы все stages.
