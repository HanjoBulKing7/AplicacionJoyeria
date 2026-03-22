package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
