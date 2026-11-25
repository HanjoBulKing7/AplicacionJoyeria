package com.jewelry.managementsystem.models;

import com.jewelry.managementsystem.constants.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private ItemStatus status;
    @Version
    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer version;

    @Column(nullable = true)
    private String image;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;


}