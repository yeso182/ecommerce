package com.arqdem.ecommerce.application.port.out;

import com.arqdem.ecommerce.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Output port: persistence contract for price lookups.
 */
public interface PriceRepository {

    Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId);
}
