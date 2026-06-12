package com.savadanko.javarepo.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Топология AMQP. В RabbitMQ продюсер шлёт сообщение НЕ напрямую в очередь, а в
 * exchange с routing key; exchange по binding-у решает, в какие очереди его
 * положить. Это главное отличие от JMS (где шлют прямо в очередь/топик).
 *
 * Объявляем три бина — Spring AMQP (через RabbitAdmin) создаст их в брокере:
 *  - Queue        — собственно очередь, где копятся сообщения;
 *  - DirectExchange — маршрутизирует по точному совпадению routing key;
 *  - Binding      — связь exchange → queue по ключу.
 */
@Configuration
public class RabbitConfig {

    public static final String QUEUE = "demo.queue";
    public static final String EXCHANGE = "demo.exchange";
    public static final String ROUTING_KEY = "demo.key";

    @Bean
    public Queue demoQueue() {
        // durable=true — очередь переживёт перезапуск брокера
        return new Queue(QUEUE, true);
    }

    @Bean
    public DirectExchange demoExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding demoBinding(Queue demoQueue, DirectExchange demoExchange) {
        return BindingBuilder.bind(demoQueue).to(demoExchange).with(ROUTING_KEY);
    }
}
