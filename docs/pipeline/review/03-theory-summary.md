# Ревью stage 03 — Сводка `docs/parameterized-tests.md`
План: docs/pipeline/stages/03-theory-summary.md   •   Раунд: 1   •   Вердикт: approved
Итог: blocking — 0 open / 0 verified;  nit — 0 open

## Что проверено
- **Формат vs `how-to-add-a-topic.md`.** Сводка `docs/parameterized-tests.md`
  следует эталонной структуре: «Что это и зачем» (краткий абзац) → «Ключевые
  понятия» (список по паре предложений) → «Абстракции/инструменты,
  использованные в коде» → «Полезно знать». Тон краткий, без воды. ✔
- **DoD stage 03:**
  - [x] файл создан в формате `how-to-add-a-topic.md`;
  - [x] все продемонстрированные источники описаны и связаны с примерами в коде;
  - [x] кратко, по делу; расширения помечены как вне v1.
- **Имена аннотаций/методов = реальный код** (сверено построчно с
  `NumberClassifierParameterizedTest`, `NumberClassifier`, `Category`):
  - `@ValueSource` → `evenNumbers`/`oddNumbers` (`isEven`/`isOdd`) — совпадает;
  - `@MethodSource` → `primeNumbers`/`notPrime`, фабрики `primes()`/`nonPrimes()`
    возвращают `IntStream` (`isPrime`) — совпадает;
  - `@CsvSource` → `classifyFromCsv(int, Category)`, строки `"2, PRIME"`,
    встроенная конверсия в `int`/`Category`, `classify` — совпадает;
  - `@EnumSource` → `everyCategoryIsConsistent` (все константы),
    `standardCategories` (`names`), `specialCategories` (`names`+`mode=EXCLUDE`),
    `Category.isStandard()` — совпадает;
  - `@NullSource`/`@EmptySource`/`@NullAndEmptySource` → `blankIsNotParsable`,
    `nullAndEmptyCombined` (+`@ValueSource`), `isParsableNumber` — совпадает.
- **Контракт SUT в тексте** (even/odd/prime, `classify → Category`,
  `isParsableNumber`, null/пусто/пробелы → false) согласован с кодом
  `NumberClassifier`/`Category`. ✔
- **Заметка про зависимость** («`junit-jupiter` уже подтягивает
  `junit-jupiter-params`») корректна: тесты используют `org.junit.jupiter.params.*`
  и проходят без отдельной зависимости (build.gradle.kts не менялся; раунд T7 —
  42 кейса зелёные).
- **Расширения вне v1** (`@CsvFileSource`, кастомные
  `ArgumentsProvider`/`ArgumentConverter`/`ArgumentsAggregator`) помечены как
  расширения темы — соответствует плану и не раздувает пример. ✔
- **Дисциплина scope.** Stage документационный: изменён только
  `docs/parameterized-tests.md`. `build.gradle.kts` не тронут (diff vs `main`
  пуст), `src/main`/`src/test` без изменений, рабочее дерево чистое. Тесты
  read-only не перезапускались — код/тесты с раунда T7 не менялись, поэтому
  состояние «зелёное» сохраняется.

## Замечания
- нет (0 blocking / 0 nit).

## Возврат к архитектору (если есть)
- нет — план stage актуален, расхождений «план ↔ реальность» не выявлено.
