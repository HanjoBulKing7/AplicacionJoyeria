package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.constants.ItemCheckStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemCheckDTO {

    private Long cartItemId;
    private ItemCheckStatus status;
    private String message;

}
