package com.savadanko.javarepo.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Интеграционный тест против РЕАЛЬНОГО брокера из docker-compose.yml.
 * Запускается только если брокер доступен на localhost:9092 (иначе тест
 * пропускается — сборка без поднятого Kafka остаётся зелёной).
 *
 * Поднять брокер:  docker compose up -d
 */
@SpringBootTest
@EnabledIf("com.savadanko.javarepo.kafka.KafkaRoundTripIT#brokerAvailable")
class KafkaRoundTripIT {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageConsumer consumer;

    @Test
    void messageIsDeliveredToListener() {
        producer.send(KafkaTopicConfig.TOPIC, "привет, Kafka");

        // доставка асинхронная — ждём, пока listener примет запись
        await().atMost(Duration.ofSeconds(20))
            .untilAsserted(() -> assertThat(consumer.getReceived()).contains("привет, Kafka"));
    }

    /** Проверка доступности брокера: пробуем открыть TCP-соединение на 9092. */
    static boolean brokerAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 9092), 1000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
