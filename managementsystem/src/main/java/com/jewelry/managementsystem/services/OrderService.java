package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.OrderDTO;
import com.jewelry.managementsystem.payload.OrderRequestDTO;

public interface OrderService {

    OrderDTO placeOrder(OrderRequestDTO orderRequest);
}
