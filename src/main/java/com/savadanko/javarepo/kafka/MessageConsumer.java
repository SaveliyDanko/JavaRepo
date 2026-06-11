package com.savadanko.javarepo.kafka;

import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Получатель. Метод с @KafkaListener вызывается для каждой записи в топике.
 * groupId задаёт consumer group: партиции топика распределяются между
 * потребителями одной группы (горизонтальное масштабирование).
 *
 * Принятые сообщения складываются в список — удобно для теста.
 */
@Component
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    private final CopyOnWriteArrayList<String> received = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = KafkaTopicConfig.TOPIC, groupId = "demo-group")
    public void listen(String message) {
        received.add(message);
        log.info("Получено: {}", message);
    }

    public CopyOnWriteArrayList<String> getReceived() {
        return received;
    }
}
