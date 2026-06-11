# JavaRepo — ветка `quartz`

Тема: **Quartz Scheduler** на Spring Boot (RAMJobStore — джобы в памяти).

> Самый мощный из планировщиков в линейке. Базовый каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/quartz/
├── QuartzApplication.java   # @SpringBootApplication (стартер сам поднимает Scheduler)
├── ReportJob.java           # Job: execute() + чтение JobDataMap
└── QuartzConfig.java        # бины JobDetail + Trigger (SimpleTrigger)
src/main/resources/application.yml   # job-store-type: memory
src/test/java/.../QuartzJobTest.java # проверка срабатывания по триггеру
```

## Теория

Краткая теоретическая сводка по теме — в [docs/quartz.md](docs/quartz.md).

## Команды

```bash
./gradlew bootRun   # запустить приложение (джоб тикает в логах)
./gradlew test      # прогнать тест
```
