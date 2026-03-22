package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    @Column(nullable = false )
    private String name;
    @Column( nullable = false)
    private String description;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Category category;
    @OneToOne
    @JoinColumn ( name = "original_item")
    private Item originalItem;


}
