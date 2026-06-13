package com.savadanko.javarepo.jpaspecification;

import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

/**
 * Переиспользуемые кусочки WHERE-условий.
 *
 * Specification<T> — функциональный интерфейс с одним методом
 *   Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
 * где:
 *   - root — «таблица» сущности: root.get("price") = столбец (типобезопасно, без строк SQL);
 *   - cb   — фабрика условий: equal, like, greaterThanOrEqualTo, isTrue, ...
 *
 * Каждый метод возвращает один предикат. Дальше их КОМБИНИРУЮТ через
 * Specification.allOf(...) / .and() / .or() — в этом вся сила: запрос
 * собирается из переиспользуемых частей под текущие фильтры.
 *
 * Утилитный класс — только статические фабрики спецификаций.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    /** category = ? */
    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    /** price >= ? */
    public static Specification<Product> priceAtLeast(BigDecimal min) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), min);
    }

    /** price <= ? */
    public static Specification<Product> priceAtMost(BigDecimal max) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), max);
    }

    /** inStock = true */
    public static Specification<Product> inStock() {
        return (root, query, cb) -> cb.isTrue(root.get("inStock"));
    }

    /** name LIKE %?% без учёта регистра */
    public static Specification<Product> nameContains(String text) {
        return (root, query, cb) ->
            cb.like(cb.lower(root.get("name")), "%" + text.toLowerCase() + "%");
    }
}
