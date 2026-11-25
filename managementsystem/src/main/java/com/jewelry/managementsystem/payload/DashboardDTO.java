package com.jewelry.managementsystem.payload;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

public record DashboardDTO(
        Long inventoryCount,
        Long activeQuantity,
        Long inactiveQuantity,
        Long categoryCount,
        BigDecimal totalSold,
        Instant generatedAt
) {}
