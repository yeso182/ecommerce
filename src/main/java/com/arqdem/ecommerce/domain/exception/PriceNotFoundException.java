package com.arqdem.ecommerce.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long productId, Integer brandId, LocalDateTime applicationDate) {
        super("No applicable price found for product %d, brand %d at %s"
                .formatted(productId, brandId, applicationDate));
    }
}
