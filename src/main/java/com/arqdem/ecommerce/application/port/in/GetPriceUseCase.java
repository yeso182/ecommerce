package com.arqdem.ecommerce.application.port.in;

import com.arqdem.ecommerce.domain.model.Price;

import java.time.LocalDateTime;

/**
 * Input port: returns the price with the highest priority applicable for the given product, brand and date.
 */
public interface GetPriceUseCase {

    Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId);
}
