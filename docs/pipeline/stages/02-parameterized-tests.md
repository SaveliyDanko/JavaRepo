# Stage 02 — Параметризованные тесты по источникам

Обновлено: 2026-06-18
Основано на: requirements 2026-06-18, architecture 2026-06-18

## Цель и место в плане
Главный обучающий артефакт темы: один тест-класс, который на примере
`NumberClassifier` показывает ключевые источники параметров JUnit 5. Каждый
тест-метод = «срез» на один источник. Соответствует шагу 3 укрупнённого плана.

**Решение о гранулярности (архитектор):** ОДИН тест-класс
`NumberClassifierParameterizedTest`, методы сгруппированы по источнику и идут
сверху вниз от простого к сложному. Так читатель видит все источники в одном
месте и сравнивает их (учебная читаемость важнее дробления на файлы).
Зафиксировано в `decisions.md`.

## Предпосылки и зависимости
- Stage 01 готов: `NumberClassifier` и `Category` существуют, контракт
  зафиксирован.
- `junit-jupiter-params` доступен транзитивно (правок сборки нет).

## Под-шаги
Файл: `src/test/java/com/savadanko/javarepo/parameterizedtests/NumberClassifierParameterizedTest.java`.
Импорты: `org.junit.jupiter.params.ParameterizedTest`, `...params.provider.*`,
статически `org.junit.jupiter.api.Assertions.*`. У каждого метода — атрибут
`name` для читаемых имён кейсов. Перед каждым методом — короткий комментарий
«что показывает этот источник».

### 1. `@ValueSource` — простой список литералов
- Метод `evenNumbers(int n)`:
  `@ValueSource(ints = {2, 4, 6, 100, -8})` → `assertTrue(classifier.isEven(n))`.
- Метод `oddNumbers(int n)`:
  `@ValueSource(ints = {1, 3, 7, 99, -5})` → `assertTrue(classifier.isOdd(n))`.
- `name = "{0} -> even/odd"`. Что показывает: один параметр-литерал на кейс.

### 2. `@MethodSource` — данные из метода-фабрики
- Метод `primeNumbers(int n)`:
  `@MethodSource("primes")` → `assertTrue(classifier.isPrime(n))`.
- Фабрика `static IntStream primes()` (или `Stream<Arguments>`), возвращает
  `2, 3, 5, 7, 11, 13`. Показать оба пути допустимо, но для краткости — `IntStream`.
- Опционально второй метод `notPrime(int n)` c `@MethodSource("nonPrimes")`
  (`0, 1, 4, 9, -3`) → `assertFalse(...)` — закрывает контракт краёв.
- Что показывает: параметры вычисляются кодом, когда литералов мало.

### 3. `@CsvSource` — пары «вход → ожидание»
- Метод `classifyFromCsv(int input, Category expected)`:
  ```
  @CsvSource({ "2, PRIME", "4, COMPOSITE", "0, ZERO", "1, ONE", "-3, NEGATIVE" })
  ```
  → `assertEquals(expected, classifier.classify(input))`.
- Что показывает: табличные кейсы; встроенная конверсия `String → int` и
  `String → enum Category` (без кастомного конвертера).

### 4. `@EnumSource` — перебор констант enum
- Метод `everyCategoryIsConsistent(Category c)`: `@EnumSource(Category.class)` →
  `assertNotNull(c)` и инвариант `assertEquals(c == Category.PRIME || c ==
  Category.COMPOSITE, c.isStandard())`.
- Метод `standardCategories(Category c)`:
  `@EnumSource(value = Category.class, names = {"PRIME", "COMPOSITE"})` →
  `assertTrue(c.isStandard())`.
- Метод `specialCategories(Category c)`:
  `@EnumSource(value = Category.class, names = {"PRIME", "COMPOSITE"}, mode =
  EnumSource.Mode.EXCLUDE)` → `assertFalse(c.isStandard())`.
- Что показывает: перебор enum целиком и фильтрация через `names` + `mode`.

### 5. Null/Empty источники — краевые ссылочные значения
- Метод `blankIsNotParsable(String text)`:
  `@NullSource @EmptySource` (две аннотации) → `assertFalse(classifier.isParsableNumber(text))`.
- Метод `nullAndEmptyCombined(String text)`:
  `@NullAndEmptySource` (+ можно добавить `@ValueSource(strings = {" ", "abc"})`)
  → `assertFalse(classifier.isParsableNumber(text))`.
- Что показывает: источники для `null`/пустых значений ссылочных типов;
  комбинирование нескольких источников на одном методе.

## Краевые случаи и риски
- `@CsvSource`: строковые значения должны точно совпадать с именами констант
  `Category` (иначе ошибка конверсии) — проверяется прогоном.
- `@MethodSource`: имя метода-фабрики в строке должно совпадать с именем
  `static`-метода; возвращаемый тип — `IntStream`/`Stream<Arguments>`.
- `@EnumSource(... mode = EXCLUDE)`: требует статического импорта/ссылки
  `EnumSource.Mode.EXCLUDE`.
- НЕ выходить за объём: без `@CsvFileSource` и кастомных провайдеров (упомянуть
  как «расширения темы» только в сводке, stage 03).

## Definition of Done
- [ ] Класс `NumberClassifierParameterizedTest` создан в пакете темы (`src/test`).
- [ ] Продемонстрированы все источники: `@ValueSource`, `@MethodSource`,
      `@CsvSource`, `@EnumSource` (включая `names`/`mode`),
      `@NullSource`/`@EmptySource`/`@NullAndEmptySource`.
- [ ] У каждого `@ParameterizedTest` задан читаемый `name`; перед методом —
      короткий поясняющий комментарий.
- [ ] `./gradlew test` зелёный; кейсы детерминированы, без сети/ресурсов.
