package com.arqdem.ecommerce.infrastructure.adapter.out.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Returns prices that cover the given date, ordered by priority descending.
     * Using Pageable to let the caller limit to a single row at the DB level.
     */
    @Query("""
            SELECT p FROM PriceEntity p
            WHERE p.productId = :productId
              AND p.brandId   = :brandId
              AND :date BETWEEN p.startDate AND p.endDate
            ORDER BY p.priority DESC
            """)
    List<PriceEntity> findApplicablePrices(
            @Param("date") LocalDateTime date,
            @Param("productId") Long productId,
            @Param("brandId") Integer brandId,
            Pageable pageable
    );
}
