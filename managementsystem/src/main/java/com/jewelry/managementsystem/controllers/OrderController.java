package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.payload.OrderDTO;
import com.jewelry.managementsystem.payload.OrderRequestDTO;
import com.jewelry.managementsystem.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/place-order")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderRequestDTO orderRequest){

        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.OK);
    }


}
