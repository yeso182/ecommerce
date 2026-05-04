package com.arqdem.ecommerce.infrastructure.adapter.out.persistence;

import com.arqdem.ecommerce.domain.model.Price;

final class PriceMapper {

    private PriceMapper() {}

    static Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
