package com.jewelry.managementsystem.payload;

public record CheckoutResponseDTO(
        OrderDTO order,
        String clientSecret
) {
}
