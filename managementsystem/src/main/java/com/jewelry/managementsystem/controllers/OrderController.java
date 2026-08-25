package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.payload.CheckoutResponseDTO;
import com.jewelry.managementsystem.payload.ConfirmPaymentDTO;
import com.jewelry.managementsystem.payload.OrderDTO;
import com.jewelry.managementsystem.payload.OrderRequestDTO;
import com.jewelry.managementsystem.services.OrderService;
import com.stripe.exception.StripeException;
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

    @PostMapping("/orders/validate")
    public ResponseEntity<CheckoutResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequest) throws StripeException {

        CheckoutResponseDTO checkoutRes = orderService.validateAndPlaceOrder(orderRequest);

        return new ResponseEntity<>(checkoutRes, HttpStatus.OK);
    }

    @PostMapping("/orders/confirm")
    public ResponseEntity<OrderDTO> confirmOrder(@RequestBody ConfirmPaymentDTO paymentConfirmed) throws StripeException {
        
        OrderDTO confirmedOrder = orderService.confirmPayment(paymentConfirmed);

        return new ResponseEntity<>(confirmedOrder, HttpStatus.OK);

    }

}