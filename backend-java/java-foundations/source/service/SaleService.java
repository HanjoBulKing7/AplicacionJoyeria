package source.service;

import domain.Sale;

import java.util.List;

public interface SaleService {


    void registerSale(Sale sale);

    List<Sale> getHistoryOfSales();



}