package source.service;

import domain.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {


    void registerSale(Sale sale);

    List<Sale> getHistoryOfSales();

    Optional<Float> getTotalRevenue();

}