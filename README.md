# JavaRepo — ветка `jakarta-ee-timer`

Тема: **Jakarta EE Timer Service** (EJB-таймеры).

> Таймеры исполняет EE-контейнер (GlassFish/Payara/WildFly). Здесь — только API:
> код компилируется против `jakarta.ejb`, тесты проверяют логику без контейнера.
> Базовый каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/eetimer/
└── ReportTimerBean.java   # @Singleton @Startup: @Schedule (авто) + TimerService/@Timeout (программный)
src/test/java/.../ReportTimerBeanTest.java   # проверка callback-логики бина
```

## Теория

Краткая теоретическая сводка по теме — в [docs/jakarta-ee-timer.md](docs/jakarta-ee-timer.md).

## Команды

```bash
./gradlew test   # прогнать тесты (без EE-контейнера)
```
