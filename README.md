# JavaRepo — ветка `rabbitmq`

Тема: **RabbitMQ в Spring** (AMQP, spring-boot-starter-amqp), брокер — внешний
через docker-compose.

> Брокер не embedded: поднимается отдельно (`docker compose up -d`). Базовый
> каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/rabbitmq/
├── RabbitApplication.java   # точка входа + CommandLineRunner (демо-отправка при старте)
├── RabbitConfig.java        # топология: Queue + DirectExchange + Binding
├── MessageProducer.java     # отправка в exchange через RabbitTemplate
└── MessageConsumer.java     # приём из очереди через @RabbitListener
src/main/resources/application.yml   # host/port/credentials брокера
docker-compose.yml                    # RabbitMQ + management UI
src/test/java/.../MessageProducerTest.java  # unit-тест (без брокера, всегда идёт)
src/test/java/.../RabbitRoundTripIT.java    # интеграционный тест (условный)
```

## Теория

Краткая теоретическая сводка по теме — в [docs/rabbitmq.md](docs/rabbitmq.md).

## Команды

```bash
./gradlew test           # unit-тест идёт всегда; round-trip — только при поднятом брокере
docker compose up -d     # RabbitMQ на :5672, UI на http://localhost:15672 (guest/guest)
./gradlew bootRun        # старт приложения: CommandLineRunner шлёт демо-сообщение, consumer его логирует
docker compose down      # остановить брокер
```
