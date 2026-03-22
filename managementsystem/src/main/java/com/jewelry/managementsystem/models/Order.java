package com.jewelry.managementsystem.models;

import com.jewelry.managementsystem.constants.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Email
    private String email;

    @OneToMany ( mappedBy = "order", cascade = { CascadeType.MERGE, CascadeType.PERSIST})
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDate orderDate;

    /// Order does not depend on Payment, can exist without it
    @OneToOne
    @JoinColumn(name="payment_id")
    private Payment payment;

    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;

    private Double totalAmount;

}
