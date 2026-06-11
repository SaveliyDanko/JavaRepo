package com.savadanko.javarepo.jms;

import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Интеграционный тест: продюсер отправляет сообщение в очередь,
 * слушатель его принимает. Брокер Artemis поднимается embedded внутри теста.
 */
@SpringBootTest
class JmsRoundTripTest {

    @Autowired
    private MessageProducer producer;

    @Autowired
    private MessageConsumer consumer;

    @Test
    void messageIsDeliveredToListener() {
        producer.send(MessageConsumer.QUEUE, "привет, JMS");

        // доставка асинхронная — ждём, пока слушатель примет сообщение
        await().atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> assertThat(consumer.getReceived()).contains("привет, JMS"));
    }
}
