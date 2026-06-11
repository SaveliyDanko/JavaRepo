# JavaRepo — ветка `scheduler`

Тема: **планировщик задач на стандартном `ScheduledExecutorService`** (`java.util.concurrent`).

> Чистый Java, без фреймворков. Базовый каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/scheduler/
├── TaskScheduler.java   # обёртка: runOnce / fixedRate / fixedDelay + close()
└── SchedulerDemo.java   # запускаемая демонстрация (main)
src/test/java/.../TaskSchedulerTest.java   # проверки выполнения и остановки
```

## Теория

Краткая теоретическая сводка по теме — в [docs/scheduler.md](docs/scheduler.md).

## Команды

```bash
./gradlew run     # запустить демонстрацию
./gradlew test    # прогнать тесты
```
