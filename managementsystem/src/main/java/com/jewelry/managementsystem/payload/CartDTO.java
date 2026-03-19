package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.models.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long cartId;
    private Double cartTotalPrice = 0.0;
    private List<CartItemDTO> cartItems = new ArrayList<CartItemDTO>();
}
