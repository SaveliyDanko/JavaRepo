package com.savadanko.javarepo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Отправитель. KafkaTemplate — Spring-обёртка над KafkaProducer: сериализует
 * ключ/значение и шлёт запись в указанный топик. Отправка асинхронная.
 */
@Component
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
