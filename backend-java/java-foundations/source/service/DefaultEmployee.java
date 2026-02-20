package source.service;

import domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DefaultEmployee {


    Sale cashSale(Customer customer, PaymentMethod paymentMethod, Employee employee,
                  LocalDateTime date, Jewel item, byte quantity);
}
