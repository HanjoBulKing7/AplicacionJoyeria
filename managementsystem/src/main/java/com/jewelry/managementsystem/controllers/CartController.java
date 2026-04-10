package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.mapper.CartItemMapper;
import com.jewelry.managementsystem.payload.CartDTO;
import com.jewelry.managementsystem.security.request.CartItemRequest;
import com.jewelry.managementsystem.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart", description = "Endpoints for managing the shopping cart and its items")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Get user cart", description = "Retrieves the current shopping cart for the authenticated user.")
    @GetMapping("/cart")
    public ResponseEntity<CartDTO> getCart() {
        CartDTO cartDTO = cartService.getCart();
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.FOUND);
    }

    @Operation(summary = "Add item to cart", description = "Adds a product to the user's cart. If the item exists, it updates the quantity.")
    @PostMapping("/cart/add")
    public ResponseEntity<CartDTO> addItem(@RequestBody CartItemRequest cartItemRequest) {
        CartDTO cartDTO = cartService.addItemToCart(cartItemRequest);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Modify cart item", description = "Updates the quantity or details of an existing item in the cart.")
    @PutMapping("/cart/cartitem")
    public ResponseEntity<CartDTO> modifyItem(@RequestBody CartItemRequest cartItemRequest) {
        CartDTO cartDTO = cartService.updateItemInCart(cartItemRequest);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove item from cart", description = "Deletes a specific product from the cart using its ID.")
    @DeleteMapping("/cart/items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        String result = cartService.deleteItemFromCart(itemId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}