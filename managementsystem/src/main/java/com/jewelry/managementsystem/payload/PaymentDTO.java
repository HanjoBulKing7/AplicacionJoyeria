package com.jewelry.managementsystem.payload;

import com.jewelry.managementsystem.constants.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long paymentId;
    private PaymentMethod paymentMethod;
    private String paymentGatewayPaymentId;
    private String paymentGatewayStatus;
    private String paymentGatewayResponse;
    private String paymentGatewayName;
}
