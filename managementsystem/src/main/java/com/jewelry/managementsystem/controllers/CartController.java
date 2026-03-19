package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.mapper.CartItemMapper;
import com.jewelry.managementsystem.payload.CartDTO;
import com.jewelry.managementsystem.security.request.CartItemRequest;
import com.jewelry.managementsystem.services.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public ResponseEntity<CartDTO> getCart() {

        CartDTO cartDTO = cartService.getCart();
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.FOUND);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<CartDTO> addItem(@RequestBody CartItemRequest cartItemRequest) {
        CartDTO cartDTO = cartService.addItemToCart(cartItemRequest);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    @PutMapping("/cart/cartitem")
    public ResponseEntity<CartDTO> modifyItem(@RequestBody CartItemRequest cartItemRequest) {
        CartDTO cartDTO = cartService.updateItemInCart(cartItemRequest);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }
    @DeleteMapping ("/cart/items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId) {
        String result = cartService.deleteItemFromCart(itemId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
