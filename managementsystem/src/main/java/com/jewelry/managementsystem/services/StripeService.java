package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.payload.StripePaymentDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;

public interface StripeService {

    PaymentIntent createPaymentIntent(StripePaymentDTO stripePaymentDTO) throws StripeException;
}
