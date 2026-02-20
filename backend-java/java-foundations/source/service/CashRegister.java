package source.service;

import domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CashRegister {

    Sale printTicket(Customer customer, PaymentMethod paymentMethod, Jewel item, byte quantity, LocalDateTime dateTime, Employee employee);

    List<Sale> getHistory();

}
