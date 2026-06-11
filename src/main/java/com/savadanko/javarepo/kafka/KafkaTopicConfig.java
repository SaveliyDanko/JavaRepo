package com.savadanko.javarepo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Объявляем топик как бин NewTopic — Spring Kafka (через KafkaAdmin) создаст
 * его в брокере при старте, если он ещё не существует.
 */
@Configuration
public class KafkaTopicConfig {

    public static final String TOPIC = "demo-topic";

    @Bean
    public NewTopic demoTopic() {
        // 1 партиция, 1 реплика — достаточно для локального брокера
        return TopicBuilder.name(TOPIC)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
