package com.savadanko.javarepo.jms;

import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель очереди. Метод, помеченный @JmsListener, вызывается Spring-ом
 * для каждого сообщения, пришедшего в указанную очередь.
 *
 * Полученные сообщения складываются в список — это удобно для тестов и
 * демонстрации. В реальном приложении тут была бы бизнес-логика.
 */
@Component
public class MessageConsumer {

    static final String QUEUE = "demo.queue";

    private final CopyOnWriteArrayList<String> received = new CopyOnWriteArrayList<>();

    @JmsListener(destination = QUEUE)
    public void onMessage(String message) {
        received.add(message);
    }

    public CopyOnWriteArrayList<String> getReceived() {
        return received;
    }
}
