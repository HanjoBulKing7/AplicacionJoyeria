package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.CheckoutResponseDTO;
import com.jewelry.managementsystem.payload.ConfirmPaymentDTO;
import com.jewelry.managementsystem.payload.OrderDTO;
import com.jewelry.managementsystem.payload.OrderRequestDTO;
import com.stripe.exception.StripeException;

public interface OrderService {

    CheckoutResponseDTO validateAndPlaceOrder(OrderRequestDTO orderRequest) throws StripeException;

    OrderDTO confirmPayment(ConfirmPaymentDTO paymentConfirmed) throws StripeException;

}
