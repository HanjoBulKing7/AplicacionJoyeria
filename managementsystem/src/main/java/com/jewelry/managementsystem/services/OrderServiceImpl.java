package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.constants.OrderStatus;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.exceptions.OrderException;
import com.jewelry.managementsystem.exceptions.ShoppingCartException;
import com.jewelry.managementsystem.mapper.OrderMapper;
import com.jewelry.managementsystem.models.*;
import com.jewelry.managementsystem.payload.*;
import com.jewelry.managementsystem.repositories.*;
import com.jewelry.managementsystem.security.services.UserDetailsImpl;
import com.jewelry.managementsystem.util.AuthUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final AuthUtil authUtil;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final StripeService stripeService;

    @Transactional
    @Override
    public CheckoutResponseDTO validateAndPlaceOrder(OrderRequestDTO orderRequest) throws StripeException {
        ///  Getting cart and address from the user
        Cart shoppingCart = cartRepository.findByEmail(authUtil.loggedInEmail());
        if(shoppingCart == null) ///  Check if shopping cart exists
            throw new ShoppingCartException("Cart not found");
        if(shoppingCart.getCartItems().isEmpty())
            throw new ShoppingCartException("Cart has no items");

        log.info("addressId: {}",  orderRequest.getAddressId());
        Address userAddress = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow( ()-> new EmptyResourceException(orderRequest.getAddressId(), "address"));

        /// Create order
        Order pendingOrder = new Order();
        pendingOrder.setAddress(userAddress);
        pendingOrder.setOrderDate(LocalDate.now());
        pendingOrder.setEmail(authUtil.loggedInEmail());
        pendingOrder.setOrderStatus(OrderStatus.PENDING);
         // TODO : ASSIGN A PAYMENT TO THE ORDER!!!!
        pendingOrder.setTotalAmount(shoppingCart.getCartTotalPrice());

        Order savedOrder = orderRepository.save(pendingOrder);

        List<OrderItem> orderItems = new ArrayList<>();

        ///  Transform all cart items into order items
        shoppingCart.getCartItems().forEach(cartItem -> {
            Item modifyItem = itemRepository.findById(cartItem.getOriginalItem().getId())
                    .orElseThrow(()-> new EmptyResourceException(cartItem.getOriginalItem().getId(), "item"));
            /// Set new quantity in stock OPTIMISTIC LOCKING HERE
            modifyItem.setStock(modifyItem.getStock()- cartItem.getQuantity());
            itemRepository.save(modifyItem);
            ///  Add to the order
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemName(cartItem.getName());
            orderItem.setOrderItemQuantity(cartItem.getQuantity());
            orderItem.setOrderItemPrice(cartItem.getPrice().doubleValue());
            orderItem.setCategory(cartItem.getCategory());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        });

        orderItemRepository.saveAll(orderItems);

        savedOrder.setOrderItems(orderItems);


        OrderDTO orderDTO = orderMapper.toDto(savedOrder);

        StripePaymentDTO stripePaymentDTO = new StripePaymentDTO(
                userAddress.getAddressId(),
                savedOrder.getOrderId(),
                (long)(shoppingCart.getCartTotalPrice() * 100),
                orderRequest.getCurrency(),
                authUtil.loggedInEmail(),
                authUtil.loggedInEmail(),
                "Jewelry order #" + savedOrder.getOrderId()
        );

        PaymentIntent paymentIntent = stripeService.createPaymentIntent(stripePaymentDTO);
        return new CheckoutResponseDTO(orderDTO, paymentIntent.getClientSecret());
    }
}
