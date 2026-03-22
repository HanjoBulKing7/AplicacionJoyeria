package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long cartItemId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String categoryName;


}
