package com.savadanko.db.transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionExample implements CommandLineRunner {

    private final OrderService service;

    public TransactionExample(OrderService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransactionExample.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            service.createOrder();
        } catch (Exception e) {
            System.out.println("⚠️ Ошибка: " + e.getMessage());
        }
    }
}

