package com.savadanko.db.transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;

    // Весь метод выполняется в одной транзакции
    @Transactional
    public void createOrder() {
        Order order = new Order("Иван Иванов");

        OrderItem item1 = new OrderItem("Ноутбук", 1);
        OrderItem item2 = new OrderItem("Мышь", 2);

        order.addItem(item1);
        order.addItem(item2);

        orderRepository.save(order);
        System.out.println("✅ Заказ сохранён, ID = " + order.getId());

        // ❌ Искусственно создадим ошибку, чтобы проверить rollback
        if (true) throw new RuntimeException("Ошибка при обработке платежа!");

        // До сюда программа не дойдёт
        System.out.println("💳 Платёж подтверждён");
    }
}

