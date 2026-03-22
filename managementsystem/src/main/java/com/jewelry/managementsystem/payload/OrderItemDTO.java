package com.jewelry.managementsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long orderItemId;
    private String orderItemName;
    private Integer orderItemQuantity;
    private Double orderItemPrice;

}
