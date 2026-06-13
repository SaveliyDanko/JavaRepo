package com.savadanko.javarepo.jpaspecification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

/**
 * Сущность-товар. Поля подобраны под разные виды фильтров, которые удобно
 * показать через Specification: текст (name/category), число-диапазон (price),
 * булев флаг (inStock).
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private BigDecimal price;
    private boolean inStock;

    protected Product() {
        // требуется JPA
    }

    public Product(String name, String category, BigDecimal price, boolean inStock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.inStock = inStock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }

    @Override
    public String toString() {
        return name + " (" + category + ", " + price + ", inStock=" + inStock + ")";
    }
}
