package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.payload.OrderDTO;
import com.jewelry.managementsystem.payload.OrderRequestDTO;
import com.jewelry.managementsystem.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders", description = "Endpoints for processing purchases and managing order lifecycle")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Place a new order",
            description = "Converts the current user's cart into a formal order. It handles stock verification, total calculation, and links the order to a specific address and payment method."
    )
    @PostMapping("/orders/place-order")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderRequestDTO orderRequest){
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.OK);
    }
}