package com.jewelry.managementsystem.builders;

import com.jewelry.managementsystem.constants.OrderStatus;
import com.jewelry.managementsystem.models.Address;
import com.jewelry.managementsystem.models.Order;
import com.jewelry.managementsystem.models.OrderItem;
import com.jewelry.managementsystem.models.Payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private Long orderId = 1L;
    private String email = "test@jewelry.com"; // hardcodeado igual que los demás defaults
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDate orderDate = LocalDate.now();
    private Payment payment = null;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private Address address = null;
    private Double totalAmount = 100.5;

    private OrderBuilder() {}

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public OrderBuilder withStatus(OrderStatus status) {
        this.orderStatus = status;
        return this;
    }

    public OrderBuilder withPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public OrderBuilder withTotalAmount(Double amount) {
        this.totalAmount = amount;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setEmail(email);
        order.setOrderItems(orderItems);
        order.setOrderDate(orderDate);
        order.setPayment(payment);
        order.setOrderStatus(orderStatus);
        order.setAddress(address);
        order.setTotalAmount(totalAmount);
        return order;
    }
}