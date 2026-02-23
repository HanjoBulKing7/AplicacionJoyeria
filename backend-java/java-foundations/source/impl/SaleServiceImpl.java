package source.impl;

import domain.Sale;
import source.service.SaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleServiceImpl implements SaleService {

    List<Sale> saleHistory = new ArrayList<>();

    @Override
    public void registerSale(Sale sale) {

        sale.getJewel().decreaseStock(sale.getQuantity());
        saleHistory.add(sale);
    }

    @Override
    public List<Sale> getHistoryOfSales() {
        return saleHistory;
    }

    @Override
    public Optional<Float> getTotalRevenue() {

        return getHistoryOfSales().stream()
                .map(Sale::getTotalAmount).reduce(Float::sum);
    }

}
