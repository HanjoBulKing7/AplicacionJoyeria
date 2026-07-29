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
    private Double amount;
    private String currency;
    private String username;
    private String pgName;
    private String description;
    private Map<String, String> metadata;
}
