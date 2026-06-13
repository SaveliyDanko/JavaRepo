package com.savadanko.javarepo.jpaspecification;

import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Демонстрация при старте приложения: наполняем H2 и гоняем динамический поиск
 * с разными наборами фильтров — в логах видно, как один search(...) выдаёт
 * разные выборки в зависимости от переданных условий.
 */
@Component
public class DemoRunner implements CommandLineRunner {

    private final ProductRepository repository;
    private final ProductSearchService search;

    public DemoRunner(ProductRepository repository, ProductSearchService search) {
        this.repository = repository;
        this.search = search;
    }

    @Override
    public void run(String... args) {
        repository.save(new Product("Клавиатура", "Периферия", new BigDecimal("2500"), true));
        repository.save(new Product("Мышь", "Периферия", new BigDecimal("1200"), false));
        repository.save(new Product("Монитор", "Дисплеи", new BigDecimal("18000"), true));
        repository.save(new Product("Коврик", "Периферия", new BigDecimal("700"), true));

        System.out.println("Периферия в наличии до 2000: "
            + search.search("Периферия", new BigDecimal("2000"), true));
        System.out.println("Всё в наличии: "
            + search.search(null, null, true));
        System.out.println("Без фильтров: "
            + search.search(null, null, null));
    }
}
