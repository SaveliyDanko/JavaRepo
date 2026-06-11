# JavaRepo — ветка `kafka`

Тема: **Apache Kafka в Spring** (spring-kafka), брокер — внешний через
docker-compose.

> Брокер не embedded: поднимается отдельно (`docker compose up -d`). Базовый
> каркас живёт в `main`.

## Что внутри

```
src/main/java/com/savadanko/javarepo/kafka/
├── KafkaApplication.java    # точка входа
├── KafkaTopicConfig.java    # объявление топика (NewTopic)
├── MessageProducer.java     # отправка через KafkaTemplate
└── MessageConsumer.java     # приём через @KafkaListener
src/main/resources/application.yml   # bootstrap-servers, сериализаторы, group-id
docker-compose.yml                    # Kafka (KRaft, без ZooKeeper) на :9092
src/test/java/.../KafkaRoundTripIT.java  # интеграционный тест (условный)
```

## Теория

Краткая теоретическая сводка по теме — в [docs/kafka.md](docs/kafka.md).

## Команды

```bash
docker compose up -d     # поднять Kafka на localhost:9092
./gradlew test           # round-trip тест (только если брокер доступен)
./gradlew bootRun        # запустить приложение
docker compose down       # остановить брокер
```
