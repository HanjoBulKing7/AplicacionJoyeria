package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.constants.ItemCheckStatus;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.exceptions.ShoppingCartException;
import com.jewelry.managementsystem.mapper.CartItemMapper;
import com.jewelry.managementsystem.mapper.CartMapper;
import com.jewelry.managementsystem.mapper.ItemMapper;
import com.jewelry.managementsystem.models.Cart;
import com.jewelry.managementsystem.models.CartItem;
import com.jewelry.managementsystem.models.Item;
import com.jewelry.managementsystem.payload.CartDTO;
import com.jewelry.managementsystem.payload.CartItemCheckDTO;
import com.jewelry.managementsystem.payload.CartItemDTO;
import com.jewelry.managementsystem.repositories.CartItemRepository;
import com.jewelry.managementsystem.repositories.CartRepository;
import com.jewelry.managementsystem.repositories.ItemRepository;
import com.jewelry.managementsystem.security.request.CartItemRequest;
import com.jewelry.managementsystem.util.AuthUtil;
import jakarta.persistence.Version;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Double.sum;

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
        CartItem cartItem = itemMapper.toCartItem(foundItem);
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
            throw new ShoppingCartException(cartItemRequest.getProductId());
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
    @Transactional
    public String deleteItemFromCart(Long productId) {
        Cart currentCart = cartRepository.findByEmail(authUtil.loggedInEmail());
        CartItem cartItem  = cartItemRepository.findByCartIdAndItemId(currentCart.getCartId(), productId);

        if(cartItem == null)
            throw new ShoppingCartException("Item not found in cart: " + productId);

        cartItemRepository.delete(cartItem);

        currentCart.getCartItems().remove(cartItem);

        Double newTotalPrice = currentCart.getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();


        currentCart.setCartTotalPrice(newTotalPrice);

        cartRepository.save(currentCart);

        return "Item with id " + productId + " deleted successfully";
    }
    @Override
    public List<CartItemCheckDTO> checkCartItemsAvailability(CartDTO currentCart){
        List<CartItemDTO> currentItems = currentCart.getCartItems();

        return currentItems.stream()
                .map(
                        uncheckedItem->{
                            CartItemCheckDTO checkedItem = new CartItemCheckDTO();

                            /// Check if the cart item is on stock
                            Item itemFromStock = itemRepository.findById(uncheckedItem.getProductId())
                                    .orElseThrow(()-> new ShoppingCartException("The product does not exist in stock"));
                            ///If exists assign from stock to the  checked list ( if not we will add something unlikely existing)
                            checkedItem.setCartItemId(itemFromStock.getId());

                            if(itemFromStock.getStock() == 0 ){
                                checkedItem.setStatus(ItemCheckStatus.OUTTA_STOCK);
                                checkedItem.setMessage("This product ran out of stock verify the shopping cart");
                            }
                            if(itemFromStock.getStock()> 0 && itemFromStock.getStock() <= 5){
                                checkedItem.setStatus(ItemCheckStatus.LOW_STOCK);
                                checkedItem.setMessage("This product is almost sold out ");
                            }
                            if(itemFromStock.getStock() > 5 )
                                checkedItem.setStatus(ItemCheckStatus.IN_STOCK);

                            return checkedItem;
                        }
                )
                .toList();

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
