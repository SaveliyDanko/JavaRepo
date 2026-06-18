# @Parameterized Tests (JUnit 5)

## Что это и зачем

Параметризованный тест — это один тест-метод, который JUnit прогоняет много
раз на разных наборах входных данных. Он убирает дублирование, когда несколько
почти одинаковых `@Test` отличаются только значениями. Метод помечается
`@ParameterizedTest` (вместо `@Test`), а источник данных задаётся отдельной
аннотацией. Каждый набор аргументов — отдельный кейс в отчёте.

Пример в коде: `NumberClassifierParameterizedTest` гоняет предикаты маленького
детерминированного SUT `NumberClassifier` (even/odd/prime, `classify → Category`,
`isParsableNumber`) на разных источниках параметров.

## Ключевые понятия

- **`@ParameterizedTest`** — замена `@Test` для метода с параметрами; запускается
  по разу на каждый набор аргументов. Атрибут `name` задаёт читаемое имя кейса в
  отчёте (плейсхолдеры `{0}`, `{1}` — значения аргументов).
- **`@ValueSource`** — массив литералов одного типа (`ints`, `strings`, …) для
  метода с одним параметром. Самый простой источник.
- **`@MethodSource`** — аргументы возвращает `static`-метод-фабрика
  (`Stream`/`IntStream`/`Stream<Arguments>`). Нужен, когда данные вычисляются или
  их удобнее построить кодом. Имя в аннотации = имя фабрики.
- **`@CsvSource`** — табличные строки «вход, ожидание»; JUnit сам конвертирует
  строку в тип параметра (примитивы и `enum`) без кастомного конвертера.
- **`@EnumSource`** — перебор констант `enum`. Без фильтра берёт все; через
  `names` + `mode` (`INCLUDE` по умолчанию / `EXCLUDE`) можно сузить набор.
- **`@NullSource` / `@EmptySource` / `@NullAndEmptySource`** — краевые значения
  для ссылочных типов (`null`, пустые строки/коллекции). Комбинируются с другими
  источниками на одном методе.

## Абстракции/инструменты, использованные в коде

Каждый источник показан отдельным «срезом» в `NumberClassifierParameterizedTest`:

- **`@ValueSource`** → `evenNumbers` / `oddNumbers` — литералы `int` против
  `isEven`/`isOdd`.
- **`@MethodSource`** → `primeNumbers` / `notPrime` (фабрики `primes()` /
  `nonPrimes()` возвращают `IntStream`) против `isPrime`.
- **`@CsvSource`** → `classifyFromCsv` — строки `"2, PRIME"` конвертируются в
  `int` и `Category`, проверяют `classify`.
- **`@EnumSource`** → `everyCategoryIsConsistent` (все константы),
  `standardCategories` (`names`) и `specialCategories` (`names` + `mode=EXCLUDE`)
  против `Category.isStandard()`.
- **`@NullSource`/`@EmptySource`/`@NullAndEmptySource`** → `blankIsNotParsable` и
  `nullAndEmptyCombined` (вместе с `@ValueSource`) против `isParsableNumber`.

Отдельная зависимость не нужна: `junit-jupiter` уже подтягивает
`junit-jupiter-params`.

## Полезно знать

- Несколько источников можно вешать на один метод (см. `nullAndEmptyCombined`:
  `@NullAndEmptySource` + `@ValueSource`).
- Запуск: `./gradlew test`.
- Вне этого примера (v1) тема расширяется: `@CsvFileSource` (данные из ресурса),
  кастомные `ArgumentsProvider`, `ArgumentConverter` и `ArgumentsAggregator` для
  своей логики конверсии/сборки аргументов.
