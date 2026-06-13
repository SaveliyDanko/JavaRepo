package com.savadanko.javarepo.jpaspecification;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

/**
 * Проверяем динамический поиск: разные наборы фильтров (часть может быть null)
 * собирают разный запрос. @DataJpaTest не сканирует @Service, поэтому втягиваем
 * сервис явно через @Import.
 */
@DataJpaTest
@Import(ProductSearchService.class)
class ProductSearchServiceTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductSearchService search;

    @BeforeEach
    void seed() {
        repository.save(new Product("Клавиатура", "Периферия", new BigDecimal("2500"), true));
        repository.save(new Product("Мышь", "Периферия", new BigDecimal("1200"), false));
        repository.save(new Product("Монитор", "Дисплеи", new BigDecimal("18000"), true));
        repository.save(new Product("Коврик", "Периферия", new BigDecimal("700"), true));
    }

    @Test
    void allFiltersApplied() {
        var result = search.search("Периферия", new BigDecimal("2000"), true);

        assertThat(result).extracting(Product::getName).containsExactly("Коврик");
    }

    @Test
    void onlyInStockFilter() {
        var result = search.search(null, null, true);

        assertThat(result).extracting(Product::getName)
            .containsExactlyInAnyOrder("Клавиатура", "Монитор", "Коврик");
    }

    @Test
    void noFiltersReturnsEverything() {
        var result = search.search(null, null, null);

        assertThat(result).hasSize(4);
    }
}
