package com.savadanko.javarepo.rabbitmq;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Unit-тест продюсера без брокера: проверяем, что send() шлёт сообщение в
 * нужный exchange с нужным routing key. RabbitTemplate замокан, поэтому тест
 * не требует Docker и всегда выполняется (в отличие от RabbitRoundTripIT).
 */
@ExtendWith(MockitoExtension.class)
class MessageProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private MessageProducer producer;

    @Test
    void sendRoutesToExchangeWithRoutingKey() {
        producer.send("hello");

        verify(rabbitTemplate)
            .convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, "hello");
    }
}
