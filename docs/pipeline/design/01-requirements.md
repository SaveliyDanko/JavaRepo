# Требования: тема «@Parameterized Tests» (JUnit 5)

Обновлено: 2026-06-18

## Проблема и цель
Учебная тема репозитория: показать параметризованные тесты JUnit 5 — как один
тест прогнать на многих наборах входных данных. Цель — закрепить понимание
механизма, а не покрыть фреймворк целиком.
**Успех:** `./gradlew test` зелёный; примеры демонстрируют ключевые источники
параметров; есть `docs/parameterized-tests.md`.

## Пользователи и сценарий
Автор репозитория (обучение Java): читает фокусный код с короткими комментариями
+ теоретическую сводку, запускает тесты.

## Объём v1
**В объёме:**
- Ветка `parameterized-tests` от чистого `main`.
- SUT: маленький класс `NumberClassifier` в `src/main` (even/odd/prime).
- Параметризованные тесты на источниках: `@ValueSource`, `@CsvSource`,
  `@MethodSource`, `@EnumSource`, `@NullSource/@EmptySource/@NullAndEmptySource`.
- `docs/parameterized-tests.md` (формат из `how-to-add-a-topic.md`).
- README ветки обновлён, со ссылкой на сводку.

**Вне объёма (v1):**
- `@CsvFileSource` (данные из файла-ресурса).
- Кастомный `ArgumentsProvider` / `ArgumentConverter` / `ArgumentsAggregator`.
- Запускаемый Demo/`main` и плагин `application`.

## Ключевые фичи по приоритету
1. `NumberClassifier` + базовые источники (`@ValueSource`, `@MethodSource`).
2. `@CsvSource` (вход→ожидание), `@EnumSource` (по категориям).
3. Null/Empty источники для граничных случаев.
4. Сводка `docs/` + README.

## Нефункциональные
Детерминированные тесты, без сети и внешних ресурсов; быстрый прогон. Читаемые
имена кейсов (паттерн `name` в `@ParameterizedTest`).

## Технические ограничения / стек
Java 21, Gradle (Kotlin DSL), JUnit 5. Отдельная зависимость не нужна:
`org.junit.jupiter:junit-jupiter` уже включает `junit-jupiter-params`. Плагин
`application` не добавлять.

## Интеграции и данные
Внешних сервисов нет. Данные — инлайн в аннотациях и `@MethodSource`.

## Проектные ограничения
Следовать `AGENTS.md` и `docs/how-to-add-a-topic.md`; `main` держать чистым.
Стиль — по аналогии с темой `cron`.
