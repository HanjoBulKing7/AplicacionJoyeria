package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.constants.OrderStatus;
import com.jewelry.managementsystem.exceptions.EmptyResourceException;
import com.jewelry.managementsystem.exceptions.OrderException;
import com.jewelry.managementsystem.exceptions.ShoppingCartException;
import com.jewelry.managementsystem.mapper.OrderMapper;
import com.jewelry.managementsystem.models.*;
import com.jewelry.managementsystem.payload.OrderDTO;
import com.jewelry.managementsystem.payload.OrderItemDTO;
import com.jewelry.managementsystem.payload.OrderRequestDTO;
import com.jewelry.managementsystem.repositories.*;
import com.jewelry.managementsystem.util.AuthUtil;
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
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public OrderDTO validateAndPlaceOrder(OrderRequestDTO orderRequest) {
        ///  Getting cart and address from the user
        Cart shoppingCart = cartRepository.findByEmail(authUtil.loggedInEmail());
        if(shoppingCart == null) ///  Check if shopping cart exists
            throw new ShoppingCartException("Cart not found");
        if(shoppingCart.getCartItems().isEmpty())
            throw new ShoppingCartException("Cart has no items");

        Address userAddress = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow( ()-> new EmptyResourceException(orderRequest.getAddressId(), "address"));
        ///  Check if total amount matches with the cartTotalAmount ( source of truth)
        if(!orderRequest.getPgTotalAmount().equals(shoppingCart.getCartTotalPrice()))
            throw new OrderException(orderRequest.getPgTotalAmount());

        /// Create order
        Order pendingOrder = new Order();
        pendingOrder.setAddress(userAddress);
        pendingOrder.setOrderDate(LocalDate.now());
        pendingOrder.setEmail(authUtil.loggedInEmail());
        pendingOrder.setOrderStatus(OrderStatus.PENDING);
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
        log.info("orderItemsDTO list: {}", orderDTO.getOrderItems());

        return orderDTO;
    }
}
