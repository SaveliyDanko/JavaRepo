# Реализация — прогресс
Источник плана: docs/pipeline/stages/   •   Обновлено: 2026-06-18 / сессия T4

## Где мы сейчас (resume here)
- Текущий stage: 01 — SUT `NumberClassifier` + `Category`
- Текущий шаг: все шаги stage 01 завершены (3/3)
- Статус шага: done
- Ретраи текущего шага: 0
- Следующее действие: ревью stage 01 (роль 04). Stage 02 НЕ начинать, пока stage 01 не `approved`.
- Состояние проверок: `./gradlew test` зелёный (baseline `MainTest`); код компилируется; изменения только в `src/main/...`, `build.gradle.kts` не тронут.

## Stages
| #  | Stage                          | Статус реализации | Готово шагов | Файл плана                    |
|----|--------------------------------|-------------------|--------------|-------------------------------|
| 01 | SUT `NumberClassifier`         | ready-for-review  | 3/3          | 01-sut-number-classifier.md   |
| 02 | Параметризованные тесты        | todo              | 0/?          | 02-parameterized-tests.md     |
| 03 | Сводка `parameterized-tests.md`| todo              | 0/?          | 03-theory-summary.md          |
| 04 | README ветки + DoD             | todo              | 0/?          | 04-readme-and-dod.md          |

## Шаги
### Stage 01 — SUT `NumberClassifier` + `Category`  [ready-for-review]
- [x] 1.1 Проверка-предпосылка (ветка `parameterized-tests` + baseline `./gradlew test` зелёный)
- [x] 1.2 Пакет темы + enum `Category`
- [x] 1.3 `NumberClassifier` (предикаты + `classify` + `isParsableNumber`)

## Журнал
- 1.1 Проверка-предпосылка — done. Сделано: активна ветка `parameterized-tests`; baseline `./gradlew test` зелёный (только `MainTest`). Отклонение от среды: `JAVA_HOME` не в PATH — использован JDK из SDKMAN (`~/.sdkman/candidates/java/current`, Temurin 21.0.10). `build.gradle.kts` не менялся.
- 1.2 enum `Category` — done. Сделано: пакет `com.savadanko.javarepo.parameterizedtests`, enum `Category{NEGATIVE,ZERO,ONE,PRIME,COMPOSITE}` + `isStandard()` (true для PRIME/COMPOSITE). Файлы: `src/main/java/com/savadanko/javarepo/parameterizedtests/Category.java`.
- 1.3 `NumberClassifier` — done. Сделано: `isEven`/`isOdd`/`isPrime` (контракт `n<2`→false, делители до `d*d<=n` в long), `classify(int)`→`Category`, `isParsableNumber(String)` (null/пусто/пробелы→false). Учебные javadoc-комментарии по стилю темы. Файлы: `src/main/java/com/savadanko/javarepo/parameterizedtests/NumberClassifier.java`. Отклонений от плана нет.
