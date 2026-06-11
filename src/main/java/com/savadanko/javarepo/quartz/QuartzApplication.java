package com.savadanko.javarepo.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring-boot-starter-quartz автоматически создаёт Scheduler и запускает его,
 * подхватывая все бины JobDetail и Trigger из контекста.
 */
@SpringBootApplication
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }
}
