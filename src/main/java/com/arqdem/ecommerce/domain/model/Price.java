package com.arqdem.ecommerce.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing an applicable price entry for a product within a brand and date range.
 */
public record Price(
        Long id,
        Integer brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceList,
        Long productId,
        Integer priority,
        BigDecimal price,
        String currency
) {}
