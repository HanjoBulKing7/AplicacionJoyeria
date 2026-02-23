package source.impl;

import domain.Jewel;
import source.service.PaymentProcessor;

public class PaymentProcessorImpl implements PaymentProcessor {

    @Override
    public float processPayment(Jewel item, byte  quantity) {

        return item.getPrice() * quantity;
    }

}
