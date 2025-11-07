package com.savadanko;

import com.savadanko.mail.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class Application implements CommandLineRunner {
    private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String... args) {
        emailService.sendMimeEmail(
                "dankosaveliy.m@gmail.com",
                "Spring Boot mail test",
                "Привет! Это тест из моего Spring Boot приложения.",
                "img.png"
        );
    }
}
