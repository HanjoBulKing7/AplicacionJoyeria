package source.impl;

import domain.*;
import source.service.CashRegister;
import source.service.DefaultEmployee;
import source.service.SaleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DefaultEmployeeImpl implements DefaultEmployee{

    //AQUI INSTANCIAR LO QUE REQUIERE EL CONSTRUCTOR DE CASHREGISTER PARA PODER INYECTAR DEPENDENCIAS Y USARLO DESDE EL DEFUALT EMPLOYEE
    private final CashRegister cashRegister;
    private final SaleService saleService;

    public DefaultEmployeeImpl(CashRegister cashRegister, SaleService saleService) {
        this.saleService = saleService;
        this.cashRegister = cashRegister;
    }

    @Override
    public Sale cashSale(Customer customer, PaymentMethod paymentMethod, Employee employee, LocalDateTime date, Jewel item, byte quantity) {

        Sale saleTicket = cashRegister.printTicket(customer,  paymentMethod, item, quantity, date, employee);

        return saleTicket;
    }

    public List<Sale> getSaleHistory(){
        return cashRegister.getHistory();
    }

    public Optional<Float> getTotalRevenue(){
         //float totalRevenue = saleService.getTotalRevenue();

        return saleService.getTotalRevenue();
    }

}
