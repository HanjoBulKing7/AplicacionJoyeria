package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
