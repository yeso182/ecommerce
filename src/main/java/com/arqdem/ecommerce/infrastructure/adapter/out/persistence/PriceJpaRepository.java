package com.arqdem.ecommerce.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
            SELECT p FROM PriceEntity p
            WHERE p.productId = :productId
              AND p.brandId   = :brandId
              AND :date BETWEEN p.startDate AND p.endDate
            ORDER BY p.priority DESC
            LIMIT 1
            """)
    Optional<PriceEntity> findTopApplicablePrice(
            @Param("date") LocalDateTime date,
            @Param("productId") Long productId,
            @Param("brandId") Integer brandId
    );
}
