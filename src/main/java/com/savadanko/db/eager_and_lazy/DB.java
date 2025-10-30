package com.savadanko.JavaRepo.db.eager_and_lazy;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DB implements CommandLineRunner {
    private final AuthorService authorService;

    public static void main(String[] args) {
        SpringApplication.run(DB.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authorService.createData();
        authorService.testLazy();
    }
}
