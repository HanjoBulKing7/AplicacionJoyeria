package source.servicer.impl;

import domain.Sale;
import source.service.SaleService;

import java.util.ArrayList;
import java.util.List;

public class SaleServiceImpl implements SaleService {

    List<Sale> saleHistory = new ArrayList<>();

    @Override
    public void registerSale(Sale sale) {
        saleHistory.add(sale);
    }

    @Override
    public List<Sale> getHistoryOfSales() {
        return saleHistory;
    }

}
