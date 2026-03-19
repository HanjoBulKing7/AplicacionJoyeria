package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.exceptions.ShoppingCartException;
import com.jewelry.managementsystem.mapper.CartItemMapper;
import com.jewelry.managementsystem.mapper.CartMapper;
import com.jewelry.managementsystem.mapper.ItemMapper;
import com.jewelry.managementsystem.models.Cart;
import com.jewelry.managementsystem.models.CartItem;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.CartDTO;
import com.jewelry.managementsystem.payload.CartItemDTO;
import com.jewelry.managementsystem.repositories.CartItemRepository;
import com.jewelry.managementsystem.repositories.CartRepository;
import com.jewelry.managementsystem.repositories.ItemRepository;
import com.jewelry.managementsystem.security.request.CartItemRequest;
import com.jewelry.managementsystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartServiceImpl implements CartService{

    private final AuthUtil authUtil;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemMapper itemMapper;
    private final CartItemMapper cartItemMapper;


    @Override
    public CartDTO getCart() {
        Cart existingCart = cartRepository.findByEmail(authUtil.loggedInEmail());
        if(existingCart == null)
            throw new EmptyResourceException("cart");

        return cartMapper.toDto(existingCart);
    }

    @Override
    public CartDTO addItemToCart(CartItemRequest cartItemRequest) {

        Cart newCart = checkBeforeCreateCart();

        Item foundItem = itemRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new EmptyResourceException(cartItemRequest.getProductId(), "item"));

        CartItem alreadyAdded = cartItemRepository.findByCartIdAndItemId(newCart.getCartId(), cartItemRequest.getProductId());

        if(alreadyAdded != null)
            throw new ShoppingCartException(alreadyAdded.getName(), " already added");
        if(foundItem.getStock() == 0)
            throw new ShoppingCartException(foundItem.getName(), " is out of stock");
        if( cartItemRequest.getQuantity() > foundItem.getStock())
            throw new ShoppingCartException(foundItem.getName(), foundItem.getStock());
        ///  Setting up the Cart Item ( item inside the shopping cart)
        CartItem cartItem = itemMapper.toCartItem(foundItem); /// Map struct to transform
        log.info("Cart mapped: {}, {}", cartItem.getPrice(),cartItem.getName());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setCart(newCart);
        cartItem.setOriginalItem(foundItem);
        // 1. Guardamos el item para que exista en DB
        cartItemRepository.save(cartItem);



        newCart.getCartItems().add(cartItem);
        newCart.setCartTotalPrice(
                    newCart.getCartItems().stream()
                            .mapToDouble(
                                    i -> i.getPrice() * i.getQuantity()
                            )
                            .sum()
        );
        cartRepository.save(newCart);
        return cartMapper.toDto(newCart);
    }

    @Override
    public CartDTO updateItemInCart(CartItemRequest cartItemRequest) {

         Cart existingCart = cartRepository.findByEmail(authUtil.loggedInEmail());
         if(existingCart == null)
             throw new  EmptyResourceException("cart");

        Item originalItem = itemRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new EmptyResourceException(cartItemRequest.getProductId(), "item"));
        CartItem itemInCart = cartItemRepository.findByCartIdAndItemId(existingCart.getCartId(), cartItemRequest.getProductId());

        if(itemInCart == null)
            throw new ShoppingCartException(itemInCart.getName(), " does not exist in the cart");
        if(originalItem.getStock() == 0)
            throw new ShoppingCartException(originalItem.getName(), " is out of stock");
        int newQuantity = itemInCart.getQuantity() + cartItemRequest.getQuantity();
        if(newQuantity < 0 )
            throw new ShoppingCartException(originalItem.getName(), " quantity cannot be negative");

        itemInCart.setQuantity(newQuantity);
        cartItemRepository.save(itemInCart);
        existingCart.setCartTotalPrice(
                existingCart.getCartItems().stream()
                        .mapToDouble(
                                i -> i.getPrice() * i.getQuantity()
                        )
                        .sum()
        );

        cartRepository.save(existingCart);

        return cartMapper.toDto(existingCart);
    }

    @Override
    public String deleteItemFromCart(Long productId) {
        Cart currentCart = cartRepository.findByEmail(authUtil.loggedInEmail());
        CartItem cartItem  = cartItemRepository.findByCartIdAndItemId(currentCart.getCartId(), productId);

        if(cartItem == null)
            throw new ShoppingCartException(productId);

        cartItemRepository.delete(cartItem);

        return "Item with id "+productId+" successfully";
    }

    private Cart checkBeforeCreateCart() {
        Cart  existingCart = cartRepository.findByEmail(authUtil.loggedInEmail());
        if( existingCart != null) return existingCart;
        else{
            Cart newCart = new Cart();
            newCart.setCartTotalPrice(0.00);
            newCart.setUser(authUtil.loggedInUser());
            Cart savedCart =  cartRepository.save(newCart);
            return savedCart;
        }
    }
}
