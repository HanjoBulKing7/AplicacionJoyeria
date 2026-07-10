package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long productId;
    private Integer quantity;

}
