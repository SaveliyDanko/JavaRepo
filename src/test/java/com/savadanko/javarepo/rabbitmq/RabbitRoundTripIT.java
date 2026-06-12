package com.savadanko.javarepo.rabbitmq;

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
 * Запускается только если RabbitMQ доступен на localhost:5672 (иначе тест
 * пропускается — сборка без брокера остаётся зелёной).
 *
 * Поднять брокер:  docker compose up -d
 */
@SpringBootTest
@EnabledIf("com.savadanko.javarepo.rabbitmq.RabbitRoundTripIT#brokerAvailable")
class RabbitRoundTripIT {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageConsumer consumer;

    @Test
    void messageIsRoutedToQueueAndConsumed() {
        producer.send("привет, Rabbit");

        // доставка асинхронная: producer -> exchange -> queue -> listener
        await().atMost(Duration.ofSeconds(10))
            .untilAsserted(() -> assertThat(consumer.getReceived()).contains("привет, Rabbit"));
    }

    /** Проверка доступности брокера: пробуем открыть TCP-соединение на 5672. */
    static boolean brokerAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 5672), 1000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
