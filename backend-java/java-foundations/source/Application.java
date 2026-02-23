package source;

import domain.*;
import domain.exception.InsufficientStockException;
import source.service.CashRegister;
import source.impl.CashRegisterImpl;
import source.impl.DefaultEmployeeImpl;
import source.impl.PaymentProcessorImpl;
import source.impl.SaleServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        /*
        Jewel ring = new Jewel("Silver ring", 200.50f, (byte)10);
        Customer juan = new Customer("Johan");
        Employee defaultEmployee = new Employee("John Doe");

        Sale lastSaleTicket = defaultEmployee.useCashRegister( juan, "cash", defaultEmployee.getName(), ring, (byte)2);

        System.out.println("================Sale ticket===================");
        System.out.println("Customer Name: " + lastSaleTicket.getCustomer().getName());
        System.out.println("Employee: " + defaultEmployee.getName() );
        System.out.println("Item: " + ring.getName());
        System.out.println("Item quantity: " + lastSaleTicket.getQuantity());
        System.out.println("Total: " + lastSaleTicket.getTotalAmount());
        System.out.println("==============================================");
        */

        //Dependencies that might change
        PaymentProcessorImpl paymentProcessor = new PaymentProcessorImpl();
        SaleServiceImpl saleService = new SaleServiceImpl();

        //Creating a cash register for my employee
        CashRegister cashRegister = new CashRegisterImpl(paymentProcessor,  saleService);
        DefaultEmployeeImpl defaultEmployee = new DefaultEmployeeImpl(cashRegister, saleService);

        //People interacting
        Employee employeeJohan = new Employee("Johan", cashRegister, saleService );
        Customer customer = new Customer("Osmar");

        /// Filling inventory
        Jewel firstJewel = new  Jewel("Ring", 800.00f, (byte)2 );
        Jewel secondJewel = new  Jewel("Earrings", 250.00f, (byte)0 );

        try {
            ///Cashing first sale
            defaultEmployee.cashSale(customer, PaymentMethod.CC, employeeJohan, LocalDateTime.now(), firstJewel, (byte) 2);

        }
        catch (InsufficientStockException i) {
            System.out.println("Error: "+i.getMessage());
        }

        try {
            ///SECOND SALE
            defaultEmployee.cashSale(customer, PaymentMethod.C, employeeJohan, LocalDateTime.now(), secondJewel, (byte) 1);
        }
        catch (InsufficientStockException i) {
            System.out.println("Error: "+i.getMessage());
        }
        /// Now checking the sales using a service and a List<Sales>
        List<Sale> currentHistory = employeeJohan.getSaleHistory();
        LocalDateTime date;
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        ///Printing all succecsful sales
        System.out.println("Now all the history of succesful sales: ");
        /// Displaying the history of sales
        currentHistory.forEach( sale -> {
            System.out.println(sale.getQuantity()+" - "+ sale.getJewel().getName()+" $ "+sale.getTotalAmount());
            System.out.println("Date: "+sale.getDate().format(customFormatter));
        });

        /// Printing total revenue
        employeeJohan.getTotalRevenue().ifPresentOrElse(
                total -> System.out.println("Total revenue: "+total),
                () -> System.out.println("There is no revenue registered so far")
        );

    }
}
