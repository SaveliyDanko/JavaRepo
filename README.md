# JavaRepo — ветка `cron`

Тема: **cron-синтаксис и Spring `CronExpression`** — разбор выражения и
вычисление моментов срабатывания без планировщика.

> Дополняет темы-планировщики (`spring-scheduled`, `quartz`): здесь — сам cron
> «в чистом виде». Базовый каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/cron/
├── CronCalculator.java   # обёртка над CronExpression: parse, next, nextRuns, isValid
└── CronDemo.java         # печатает ближайшие запуски для набора выражений
src/test/java/.../CronCalculatorTest.java   # детерминированные проверки
```

## Теория

Краткая теоретическая сводка по теме — в [docs/cron.md](docs/cron.md).

## Команды

```bash
./gradlew run    # демонстрация ближайших срабатываний
./gradlew test   # прогнать тесты
```
