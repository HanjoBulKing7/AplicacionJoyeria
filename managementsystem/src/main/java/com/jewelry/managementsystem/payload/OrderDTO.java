package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long orderId;
    private String email;
    private List<OrderItemDTO> orderItems;
    private PaymentDTO payment;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private AddressDTO address;
}
