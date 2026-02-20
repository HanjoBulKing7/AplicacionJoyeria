package source.servicer.impl;

import domain.Jewel;
import source.service.PaymentProcessor;

public class PaymentProcessorImpl implements PaymentProcessor {

    @Override
    public float processPayment(Jewel item, byte  quantity) {

        item.decreaseStock(quantity);

        return item.getPrice() * quantity;
    }

}
