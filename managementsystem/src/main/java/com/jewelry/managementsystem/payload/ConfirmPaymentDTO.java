package com.jewelry.managementsystem.payload;

public record ConfirmPaymentDTO(
        Long orderId,
        String paymentId,
        String pgRes
) { }