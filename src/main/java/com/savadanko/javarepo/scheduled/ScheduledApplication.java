package com.savadanko.javarepo.scheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableScheduling включает поддержку аннотации @Scheduled во всём приложении.
 * Без неё методы с @Scheduled просто не будут вызываться.
 */
@SpringBootApplication
@EnableScheduling
public class ScheduledApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduledApplication.class, args);
    }
}
