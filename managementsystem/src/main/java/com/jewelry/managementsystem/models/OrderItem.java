package com.jewelry.managementsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long orderItemId;

    private String orderItemName;
    private Integer orderItemQuantity;
    private Double orderItemPrice;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn( name="order_id")
    private Order order;


}
