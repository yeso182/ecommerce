package com.arqdem.ecommerce.infrastructure.adapter.out.persistence;

import com.arqdem.ecommerce.application.port.out.PriceRepository;
import com.arqdem.ecommerce.domain.model.Price;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PricePersistenceAdapter implements PriceRepository {

    private final PriceJpaRepository jpaRepository;

    public PricePersistenceAdapter(PriceJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    /**
     * {@inheritDoc}
     * Delegates to JPA with a page size of 1 so only the top-priority row is fetched.
     */
    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId) {
        return jpaRepository
                .findApplicablePrices(applicationDate, productId, brandId, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .map(PriceMapper::toDomain);
    }
}
