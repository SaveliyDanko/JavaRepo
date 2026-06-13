package com.savadanko.javarepo.jpaspecification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

/**
 * @DataJpaTest поднимает только JPA-слой + встроенную H2 — тест идёт всегда,
 * без Docker. Проверяем как одиночные спеки, так и их композицию: главное в
 * JpaSpecificationExecutor — что запрос собирается из переиспользуемых частей.
 */
@DataJpaTest
class ProductSpecificationsTest {

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void seed() {
        repository.save(new Product("Клавиатура", "Периферия", new BigDecimal("2500"), true));
        repository.save(new Product("Мышь", "Периферия", new BigDecimal("1200"), false));
        repository.save(new Product("Монитор", "Дисплеи", new BigDecimal("18000"), true));
        repository.save(new Product("Коврик", "Периферия", new BigDecimal("700"), true));
    }

    @Test
    void filtersBySingleSpecification() {
        var result = repository.findAll(ProductSpecifications.hasCategory("Периферия"));

        assertThat(result).extracting(Product::getName)
            .containsExactlyInAnyOrder("Клавиатура", "Мышь", "Коврик");
    }

    @Test
    void combinesSpecificationsWithAnd() {
        // Периферия И в наличии И ценой <= 2000  ->  только Коврик
        Specification<Product> spec = ProductSpecifications.hasCategory("Периферия")
            .and(ProductSpecifications.inStock())
            .and(ProductSpecifications.priceAtMost(new BigDecimal("2000")));

        var result = repository.findAll(spec);

        assertThat(result).extracting(Product::getName).containsExactly("Коврик");
    }

    @Test
    void combinesSpecificationsWithOr() {
        // дешевле 1000 ИЛИ дороже 10000  ->  Коврик и Монитор
        Specification<Product> spec = ProductSpecifications.priceAtMost(new BigDecimal("1000"))
            .or(ProductSpecifications.priceAtLeast(new BigDecimal("10000")));

        var result = repository.findAll(spec);

        assertThat(result).extracting(Product::getName)
            .containsExactlyInAnyOrder("Коврик", "Монитор");
    }

    @Test
    void caseInsensitiveNameSearch() {
        var result = repository.findAll(ProductSpecifications.nameContains("мон"));

        assertThat(result).extracting(Product::getName, Product::getCategory)
            .containsExactly(tuple("Монитор", "Дисплеи"));
    }
}
