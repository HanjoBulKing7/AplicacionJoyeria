package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.constants.OrderStatus;
import com.jewelry.managementsystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0.0) FROM Order o WHERE o.orderStatus = :orderStatus")
    BigDecimal sumTotalByStatus( @Param("orderStatus") OrderStatus orderStatus);
}
