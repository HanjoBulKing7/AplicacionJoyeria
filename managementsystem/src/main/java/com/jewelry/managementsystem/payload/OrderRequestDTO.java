package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long addressId;
    private String currency;
    private String pgName;
}
