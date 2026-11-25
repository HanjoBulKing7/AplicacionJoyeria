package com.jewelry.managementsystem.services;

import com.jewelry.managementsystem.constants.ItemStatus;
import com.jewelry.managementsystem.constants.OrderStatus;
import com.jewelry.managementsystem.payload.DashboardDTO;
import com.jewelry.managementsystem.repositories.CategoryRepository;
import com.jewelry.managementsystem.repositories.ItemRepository;
import com.jewelry.managementsystem.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public DashboardDTO getDashboardStats() {

        Long itemsInStock = itemRepository.countByStockGreaterThan(0);
        Long activeItems = itemRepository.countByStatus(ItemStatus.ACTIVE);
        Long inactiveItems = itemRepository.countByStatus(ItemStatus.INACTIVE);
        Long categoryCount = categoryRepository.count();
        BigDecimal totalSold = orderRepository.sumTotalByStatus(OrderStatus.SUCCEED);

        Instant generatedAt = Instant.now();

        return new DashboardDTO(
                itemsInStock,
                activeItems,
                inactiveItems,
                categoryCount,
                totalSold,
                generatedAt
        );
    }
}
