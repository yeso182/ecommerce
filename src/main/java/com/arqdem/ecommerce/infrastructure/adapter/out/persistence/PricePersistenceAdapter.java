package com.arqdem.ecommerce.infrastructure.adapter.out.persistence;

import com.arqdem.ecommerce.application.port.out.PriceRepository;
import com.arqdem.ecommerce.domain.model.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PricePersistenceAdapter implements PriceRepository {

    private final PriceJpaRepository jpaRepository;

    public PricePersistenceAdapter(PriceJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId) {
        return jpaRepository
                .findTopApplicablePrice(applicationDate, productId, brandId)
                .map(PriceMapper::toDomain);
    }
}
