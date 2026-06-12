package com.savadanko.javarepo.rabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
    }

    /**
     * Демонстрация при старте: шлём сообщение в exchange. Консьюмер
     * (@RabbitListener) подхватит его из очереди и залогирует. Нужен поднятый
     * брокер (docker compose up -d), иначе приложение не сможет подключиться.
     */
    @Bean
    CommandLineRunner demo(MessageProducer producer) {
        return args -> producer.send("привет из CommandLineRunner");
    }
}
