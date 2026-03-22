package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long addressId;
    private PaymentMethod paymentMethod;
    private String pgName;
    private String pgPaymentId;
    private String pgStatus;
    private Double pgTotalAmount;
}
