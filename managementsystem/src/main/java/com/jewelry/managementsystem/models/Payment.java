package com.jewelry.managementsystem.models;

import com.jewelry.managementsystem.constants.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @OneToOne (mappedBy = "payment", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Order order;

    private PaymentMethod paymentMethod;

    private String paymentGatewayPaymentId;
    private String paymentGatewayStatus;
    private String paymentGatewayResponse;
    private String paymentGatewayName;


    public Payment( String paymentGatewayPaymentId, PaymentMethod paymentMethod, String paymentGatewayStatus, String paymentGatewayResponse, String paymentGatewayName) {
        this.paymentGatewayPaymentId = paymentGatewayPaymentId;
        this.paymentMethod = paymentMethod;
        this.paymentGatewayStatus = paymentGatewayStatus;
        this.paymentGatewayResponse = paymentGatewayResponse;
        this.paymentGatewayName = paymentGatewayName;
    }

}
