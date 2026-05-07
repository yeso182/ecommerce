package com.arqdem.ecommerce.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "prices",
        indexes = @Index(name = "idx_prices_lookup", columnList = "product_id, brand_id, start_date, end_date")
)
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id", nullable = false)
    private Integer brandId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "price_list", nullable = false)
    private Integer priceList;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    protected PriceEntity() {}

    public PriceEntity(Long id, Integer brandId, LocalDateTime startDate, LocalDateTime endDate,
                       Integer priceList, Long productId, Integer priority, BigDecimal price, String currency) {
        this.id = id;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    public Long getId() { return id; }
    public Integer getBrandId() { return brandId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Integer getPriceList() { return priceList; }
    public Long getProductId() { return productId; }
    public Integer getPriority() { return priority; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
}
