package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.exceptions.ResourceNotFound;
import com.jewelry.managementsystem.models.Address;
import com.jewelry.managementsystem.payload.StripePaymentDTO;
import com.jewelry.managementsystem.repositories.AddressRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    private AddressRepository addressRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(StripePaymentDTO stripePaymentDTO) throws StripeException {
        Customer customer;

        CustomerSearchParams searchExistingParams =
                CustomerSearchParams.builder().setQuery("email '"+stripePaymentDTO.email())
                        .build();
        CustomerSearchResult customers = Customer.search(searchExistingParams);

        Address address = addressRepository.findById(stripePaymentDTO.addressId())
                .orElseThrow( ()-> new ResourceNotFound("Address ", "address id:", stripePaymentDTO.addressId().toString()));


        if(customers.getData().isEmpty()){
            CustomerCreateParams newCustomerParams =
                    CustomerCreateParams.builder()
                            .setName(stripePaymentDTO.name())
                            .setEmail(stripePaymentDTO.email())
                            .setAddress(
                                    CustomerCreateParams.Address.builder()
                                            .setLine1(address.getStreet())
                                            .setLine2(address.getNeighborhood())
                                            .setCity(address.getCity())
                                            .setState(address.getState())
                                            .setPostalCode(address.getZipCode())
                                            .setCountry(address.getCountry())
                                            .build()
                            )
                            .build();
            customer = Customer.create(newCustomerParams);

        }else
            customer = customers.getData().getFirst();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(stripePaymentDTO.amount())
                        .setCurrency(stripePaymentDTO.currency())
                        .setCustomer(customer.getId())
                        .setDescription(stripePaymentDTO.description())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();


        return PaymentIntent.create(params);
    }

    @Override
    public Event validateWebHook(String payload, String sigHeader) {
        return null;
    }
}
