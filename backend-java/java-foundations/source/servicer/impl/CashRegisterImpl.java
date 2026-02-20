package source.servicer.impl;

import domain.*;
import source.service.CashRegister;
import source.service.DefaultEmployee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CashRegisterImpl implements CashRegister {


    final PaymentProcessorImpl paymentProcessor;
    final SaleServiceImpl saleService;

    public CashRegisterImpl(PaymentProcessorImpl  paymentProcessor, SaleServiceImpl saleService ) {
        this.paymentProcessor =  paymentProcessor;
        this.saleService = saleService;
    }

    @Override
    public Sale printTicket(Customer customer, PaymentMethod paymentMethod,
                            Jewel item, byte quantity, LocalDateTime dateTime, Employee employee) {
        Sale  newSale = new Sale();
        newSale.setCustomer(customer);
        newSale.setPaymentMethod(paymentMethod);
        newSale.setTotalAmount(paymentProcessor.processPayment(item,  quantity));
        newSale.setQuantity(quantity);
        newSale.setJewel(item);
        newSale.setDate(dateTime);

        saleService.registerSale(newSale);

        return newSale;
    }

    @Override
    public List<Sale> getHistory(){

        return saleService.getHistoryOfSales();
    }
}
