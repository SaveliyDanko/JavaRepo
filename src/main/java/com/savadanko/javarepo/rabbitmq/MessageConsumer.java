package com.savadanko.javarepo.rabbitmq;

import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Получатель. @RabbitListener подписывается на ОЧЕРЕДЬ (не на exchange) и
 * вызывается для каждого сообщения. Принятые сообщения копим в список — для теста.
 */
@Component
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    private final CopyOnWriteArrayList<String> received = new CopyOnWriteArrayList<>();

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receive(String message) {
        received.add(message);
        log.info("Получено: {}", message);
    }

    public CopyOnWriteArrayList<String> getReceived() {
        return received;
    }
}
