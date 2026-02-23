package source.service;

import domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DefaultEmployee {


    Sale cashSale(Customer customer, PaymentMethod paymentMethod, Employee employee,
                  LocalDateTime date, Jewel item, byte quantity);

    public List<Sale> getSaleHistory();

    public Optional<Float> getTotalRevenue();
}
