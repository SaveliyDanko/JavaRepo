package com.savadanko.db.transactional;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public OrderItem(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

