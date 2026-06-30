package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.models.Cart;
import com.jewelry.managementsystem.payload.CartDTO;
import com.jewelry.managementsystem.payload.CartItemCheckDTO;
import com.jewelry.managementsystem.payload.CartItemDTO;
import com.jewelry.managementsystem.security.request.CartItemRequest;

import java.util.List;

public interface CartService {

    CartDTO getCart();
    CartDTO addItemToCart(CartItemRequest cartItemRequest);
    CartDTO updateItemInCart(CartItemRequest cartItemRequest);
    String deleteItemFromCart(Long cartId);

    List<CartItemCheckDTO> checkCartItemsAvailability(CartDTO currentCart);

    String processCart();
}
