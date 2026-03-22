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

    @Transactional
    @Override
    public OrderDTO placeOrder(OrderRequestDTO orderRequest) {
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
        Order orderReady = new Order();
        orderReady.setAddress(userAddress);
        orderReady.setOrderDate(LocalDate.now());
        orderReady.setEmail(authUtil.loggedInEmail());
        orderReady.setOrderStatus(OrderStatus.SUCCED);
        orderReady.setTotalAmount(shoppingCart.getCartTotalPrice());

        ///  Create and save payment to add to the order
        Payment payment = new Payment(
                orderRequest.getPgPaymentId(),
                orderRequest.getPaymentMethod(),
                orderRequest.getPgStatus(),
                "Payment completed",
                orderRequest.getPgName()
        );

        payment.setOrder(orderReady); ///
        payment = paymentRepository.save(payment);
        orderReady.setPayment(payment);
        Order savedOrder = orderRepository.save(orderReady);

        List<OrderItem> orderItems = new ArrayList<>();

        ///  Transform all cart items into order items
        shoppingCart.getCartItems().forEach(cartItem -> {
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
