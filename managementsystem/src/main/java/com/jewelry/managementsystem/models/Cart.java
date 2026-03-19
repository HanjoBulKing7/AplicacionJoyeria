package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToOne
    @JoinColumn ( name ="user_id")
    private User user;
    @OneToMany ( mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();
    private Double cartTotalPrice;
}
