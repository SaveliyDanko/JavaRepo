# Stage 01 — SUT `NumberClassifier` + `Category`

Обновлено: 2026-06-18
Основано на: requirements 2026-06-18, architecture 2026-06-18

## Цель и место в плане
Реализовать SUT (system under test) — маленький детерминированный класс
`NumberClassifier` и enum `Category`. Это объект, который будут «прогонять»
параметризованные тесты (stage 02). Соответствует шагу 2 укрупнённого плана;
шаг 1 (ветка/сборка) сведён сюда как проверка-предпосылка — отдельных правок
не требует.

## Предпосылки и зависимости
- Зависимостей от других stages нет.
- Ветка `parameterized-tests` ответвлена от чистого `main` (уже так).
- `build.gradle.kts` менять НЕ нужно: `org.junit.jupiter:junit-jupiter` уже
  тянет `junit-jupiter-params` (решение зафиксировано в `design/02-architecture.md`).

## Под-шаги

### 1. Проверка-предпосылка (ветка + сборка)
- Что делаем: убедиться, что активна ветка `parameterized-tests`, базовый
  `./gradlew test` зелёный (есть только `MainTest`), правок зависимостей нет.
- Файлы: `build.gradle.kts` (только читаем — не меняем).
- Проверка: `./gradlew test` зелёный до начала работы.

### 2. Создать пакет темы и enum `Category`
- Что делаем: завести пакет `com.savadanko.javarepo.parameterizedtests` и enum
  `Category` — результат классификации числа.
- Файл: `src/main/java/com/savadanko/javarepo/parameterizedtests/Category.java`.
- Структура данных (контракт enum):
  ```
  public enum Category {
      NEGATIVE, ZERO, ONE, PRIME, COMPOSITE;
      // «стандартные» = обычные положительные >1: PRIME или COMPOSITE.
      // NEGATIVE/ZERO/ONE — особые краевые случаи. Метод даёт естественный
      // предикат для демонстрации @EnumSource с фильтром names/mode (stage 02).
      public boolean isStandard() { return this == PRIME || this == COMPOSITE; }
  }
  ```
- Проверка: компилируется; 5 констант, `isStandard()` истинно ровно для
  `PRIME`/`COMPOSITE`.

### 3. Реализовать `NumberClassifier`
- Что делаем: предикаты even/odd/prime, классификация `classify(int)` →
  `Category`, и строковый предикат `isParsableNumber(String)` для демонстрации
  Null/Empty источников (они применимы к ссылочным типам, не к `int`).
- Файл: `src/main/java/com/savadanko/javarepo/parameterizedtests/NumberClassifier.java`.
- Контракт (ключевые решения):
  - `boolean isEven(int n)` → `n % 2 == 0`.
  - `boolean isOdd(int n)` → `!isEven(n)` (корректно для отрицательных: `-3` нечётно).
  - `boolean isPrime(int n)`: **`n < 2` → `false`** (0, 1 и любые отрицательные
    НЕ простые — однозначный контракт на краях); далее проверка делителей до
    `d*d <= n` (через `long`, чтобы не переполнить у больших `n`).
  - `Category classify(int n)`:
    `n < 0`→`NEGATIVE`; `n == 0`→`ZERO`; `n == 1`→`ONE`;
    иначе `isPrime(n) ? PRIME : COMPOSITE`.
  - `boolean isParsableNumber(String text)`: `null`/пустая/из пробелов → `false`;
    иначе `true`, если `Integer.parseInt(text.trim())` не бросает
    `NumberFormatException`. Чисто, без побочных эффектов — годится для
    `@NullSource`/`@EmptySource`/`@NullAndEmptySource`.
- Комментарии: короткие учебные («что/зачем», не пересказ синтаксиса), по
  стилю `how-to-add-a-topic.md`.
- Проверка: компилируется; контракт покрыт тестами в stage 02.

## Краевые случаи и риски
- `isPrime`: 0, 1, отрицательные → `false`; 2 → `true` (наименьшее простое).
- Переполнение при больших `n` в цикле primality — снимается `long`-сравнением `d*d`.
- `isParsableNumber`: `null` не должен бросать NPE; пустая строка и пробелы → `false`.
- Объём: НЕ добавлять `@CsvFileSource`, кастомные провайдеры, `main`/`application`
  (вне v1).

## Definition of Done
- [ ] Пакет `...parameterizedtests` создан; `Category` и `NumberClassifier` на месте.
- [ ] Контракт реализован как выше (особенно `isPrime` на краях).
- [ ] Короткие учебные комментарии присутствуют.
- [ ] `./gradlew test` компилируется и зелёный (на этом шаге — ещё без новых тестов).
- [ ] Изменения только в `src/main/...`; `build.gradle.kts` не тронут.
