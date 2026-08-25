package com.jewelry.managementsystem.models;

import com.jewelry.managementsystem.constants.PaymentGateway;
import com.jewelry.managementsystem.constants.PaymentMethod;
import com.jewelry.managementsystem.constants.PaymentStatus;
import jakarta.annotation.Nullable;
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

    @Enumerated ( EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Nullable
    private String paymentGatewayPaymentId;

    @Enumerated ( EnumType.STRING)
    private PaymentStatus paymentGatewayStatus;

    @Nullable
    private String paymentGatewayResponse;

    @Enumerated ( EnumType.STRING)
    private PaymentGateway paymentGatewayName;

    public Payment(@Nullable String paymentGatewayPaymentId, PaymentMethod paymentMethod, PaymentStatus paymentGatewayStatus, @Nullable String paymentGatewayResponse, PaymentGateway paymentGatewayName) {
        this.paymentGatewayPaymentId = paymentGatewayPaymentId;
        this.paymentMethod = paymentMethod;
        this.paymentGatewayStatus = paymentGatewayStatus;
        this.paymentGatewayResponse = paymentGatewayResponse;
        this.paymentGatewayName = paymentGatewayName;
    }

}
