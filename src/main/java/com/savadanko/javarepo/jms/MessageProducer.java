package com.savadanko.javarepo.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Отправитель сообщений. JmsTemplate инкапсулирует подключение к брокеру,
 * создание сессии и продюсера — нам остаётся только указать очередь и payload.
 */
@Component
public class MessageProducer {

    private final JmsTemplate jmsTemplate;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String destination, String message) {
        jmsTemplate.convertAndSend(destination, message);
    }
}
