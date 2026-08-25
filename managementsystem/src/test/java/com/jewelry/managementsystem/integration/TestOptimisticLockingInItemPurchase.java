package com.jewelry.managementsystem.integration;

import com.jewelry.managementsystem.builders.CategoryBuilder;
import com.jewelry.managementsystem.builders.CategoryIntegrationBuilder;
import com.jewelry.managementsystem.builders.ItemBuilder;
import com.jewelry.managementsystem.models.*;
import com.jewelry.managementsystem.payload.OrderRequestDTO;
import com.jewelry.managementsystem.repositories.*;
import com.jewelry.managementsystem.services.OrderService;
import com.jewelry.managementsystem.services.StripeService;
import com.jewelry.managementsystem.util.AuthUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class TestOptimisticLockingInItemPurchase {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private CartItemRepository  cartItemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @MockBean
    private StripeService stripeService;
    @MockBean
    private AuthUtil authUtil;


    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("jewelry_test")
            .withUsername("postgres")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterEach
    void tearDown() {
        refreshTokenRepository.deleteAllInBatch();
        orderItemRepository.deleteAllInBatch();
        cartItemRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        addressRepository.deleteAllInBatch();
        cartRepository.deleteAllInBatch();
        itemRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }



    @Test
    public void shouldThrowOptimisticLockingExceptionInPurchase() throws InterruptedException, StripeException {

        /// ARRANGE
        String testEmail = "usuario@test.com";

        // Set up the mocks
        Mockito.when(authUtil.loggedInEmail()).thenReturn(testEmail);
        // Simulate Stripe returns a valid payment intent & not call real API to avoid multiple calls
        PaymentIntent mockPaymentIntent = new PaymentIntent();
        Mockito.when(stripeService.createPaymentIntent(any())).thenReturn(mockPaymentIntent);

        ///  Fill database
        Address address = new Address();
        address.setStreet("Franciso I. Madero");
        address.setNeighborhood("Zona Centro");
        address.setCity("Aguascalientes");
        address.setState("Aguascalientes");
        address.setCountry("Mexico");
        address.setZipCode("20000");
        address = addressRepository.save(address);

        Category ringsCategory = CategoryBuilder.aCategory().withId(null).withName("Rings").build();
        ringsCategory = categoryRepository.save(ringsCategory);

        User testUser = new User();
        testUser.setEmail("usuario@test.com");
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);

        ///  Just one item with quantity of 1
        Item item = ItemBuilder.anItem().withId(null).withName("Gold Ring").withStock(1).withDescription("Test Gold Ring").withPrice(100.5F).withCategory(ringsCategory).build();
        item = itemRepository.save(item);

        Cart cart = new Cart();
        cart.setUser(testUser);

        CartItem cartItem = new CartItem();
        cartItem.setOriginalItem(item);
        cartItem.setCategory(item.getCategory());
        cartItem.setName(item.getName());
        cartItem.setDescription(item.getDescription());
        cartItem.setPrice(item.getPrice());
        cartItem.setQuantity(1);

        cartItem.setCart(cart);

        cart.getCartItems().add(cartItem);

        // Simulate that I am getting the total

        Double testCartTotal = cart.getCartItems().stream()
                        .mapToDouble(CartItem::getPrice)
                                .sum();

        cart.setCartTotalPrice(testCartTotal);
        ///  Since " Cart " persits all the cart items we do not need to save the cart items after the creation
        cartRepository.save(cart);

        OrderRequestDTO request = new OrderRequestDTO();
        request.setAddressId(address.getAddressId());
        request.setPgName("stripe");
        request.setCurrency("mxn");

        /// 2. ACT ( TRY CONCURRENCY )
        int numberOfThreads = 2;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable concurrentPurchase = () -> {
            try {
                latch.await(); // Threads will wait until latch gets to 0
                orderService.validateAndPlaceOrder(request);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        };

        Future<?> future1 = executor.submit(concurrentPurchase);
        Future<?> future2 = executor.submit(concurrentPurchase);

        // Execute latch to start both threads
        latch.countDown();


        // 3. ASSERT (check for results)
        int successCount = 0;
        int optimisticLockExceptionCount = 0;

        // Check the first thread
        try {
            future1.get();
            successCount++;
        } catch (ExecutionException e) {
            if (contieneOptimisticLock(e)) {
                optimisticLockExceptionCount++;
            }
        }

        // Check the second thread
        try {
            future2.get();
            successCount++;
        } catch (ExecutionException e) {
            if (contieneOptimisticLock(e)) {
                optimisticLockExceptionCount++;
            }
        }

        executor.shutdown();

        // Validar resultados esperados
        assertEquals(1, successCount, "Una compra debió ser exitosa");
        assertEquals(1, optimisticLockExceptionCount, "La otra compra debió fallar por Optimistic Locking");

        // Validar el stock final del artículo
        Item finalItem = itemRepository.findById(item.getId()).orElseThrow();
        assertEquals(0, finalItem.getStock());
    }

    // Método auxiliar (agrégalo abajo en tu clase de prueba)
    private boolean contieneOptimisticLock(Throwable ex) {
        while (ex != null) {
            if (ex instanceof org.springframework.orm.ObjectOptimisticLockingFailureException) {
                return true;
            }
            ex = ex.getCause();
        }
        return false;
    }
}