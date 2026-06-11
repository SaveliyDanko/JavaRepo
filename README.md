# JavaRepo — ветка `jms`

Тема: **JMS (Java Message Service)** на Spring + ActiveMQ Artemis.

> Эта ветка содержит зависимости под изучение JMS. Чистый каркас живёт в `main`.

## Стек

- **Java 21**, **Gradle** (Kotlin DSL)
- **Spring Boot 3.3.x** + `spring-boot-starter-artemis`
- **ActiveMQ Artemis** — брокер запускается **embedded** (внутри процесса, ставить ничего не нужно)
- Тесты: JUnit 5 + Awaitility (ожидание асинхронной доставки)

## Что внутри

```
src/main/java/com/savadanko/javarepo/jms/
├── JmsApplication.java     # точка входа, @EnableJms
├── MessageProducer.java    # отправка через JmsTemplate.convertAndSend
└── MessageConsumer.java    # приём через @JmsListener
src/main/resources/application.yml   # Artemis в режиме embedded, очередь demo.queue
src/test/java/.../JmsRoundTripTest.java  # отправка → приём
```

## Ключевые понятия JMS

- **Destination** — куда шлём: `Queue` (point-to-point, один получатель) или `Topic` (pub/sub, все подписчики).
- **Producer / Consumer** — отправитель и получатель сообщений.
- **JmsTemplate** — Spring-обёртка для отправки/синхронного приёма.
- **@JmsListener** — декларативный асинхронный слушатель очереди.

## Команды

```bash
./gradlew test       # прогнать round-trip тест (поднимет embedded-брокер)
./gradlew bootRun    # запустить приложение
```

## Дальше можно попробовать

- Topic вместо Queue (pub/sub), несколько слушателей
- Объектные сообщения (сериализация POJO), `MessageConverter`
- Транзакции, ручной `acknowledge`, обработка ошибок и DLQ
- Внешний брокер: `spring.artemis.mode: native` + `broker-url`
