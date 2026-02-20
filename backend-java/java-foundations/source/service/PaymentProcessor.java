package source.service;

import domain.Jewel;

public interface PaymentProcessor {

    float processPayment(Jewel item, byte quantity);


}
