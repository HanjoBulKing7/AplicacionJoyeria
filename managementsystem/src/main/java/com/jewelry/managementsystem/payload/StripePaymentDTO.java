package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.models.Address;

import java.util.Map;

public record StripePaymentDTO(
        Long addressId,
        Long orderId,
        Long amount,
        String currency,
        String email,
        String name,
        String description
) { }