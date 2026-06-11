# JavaRepo — ветка `spring-scheduled`

Тема: **`@Scheduled` в Spring** — декларативный запуск задач по расписанию.

> Spring-аналог темы `scheduler` (ScheduledExecutorService). Базовый каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/scheduled/
├── ScheduledApplication.java   # @SpringBootApplication + @EnableScheduling
└── ScheduledTasks.java         # методы с @Scheduled: fixedRate / fixedDelay / cron
src/test/java/.../ScheduledTasksTest.java   # проверка срабатывания расписаний
```

## Теория

Краткая теоретическая сводка по теме — в [docs/spring-scheduled.md](docs/spring-scheduled.md).

## Команды

```bash
./gradlew bootRun   # запустить приложение (видны тики в логах)
./gradlew test      # прогнать тесты
```
