package com.arqdem.ecommerce.infrastructure.adapter.in.web.dto;

import com.arqdem.ecommerce.domain.model.Price;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Applicable price for the queried product, brand and date")
public record PriceResponse(

        @Schema(description = "Product identifier", example = "35455")
        Long productId,

        @Schema(description = "Brand identifier", example = "1")
        Integer brandId,

        @Schema(description = "Price list (tariff) identifier", example = "2")
        Integer priceList,

        @Schema(description = "Start of the applicable date range", example = "2020-06-14T15:00:00")
        LocalDateTime startDate,

        @Schema(description = "End of the applicable date range", example = "2020-06-14T18:30:00")
        LocalDateTime endDate,

        @Schema(description = "Final selling price", example = "25.45")
        BigDecimal price,

        @Schema(description = "ISO 4217 currency code", example = "EUR")
        String currency
) {
    public static PriceResponse from(Price domain) {
        return new PriceResponse(
                domain.productId(),
                domain.brandId(),
                domain.priceList(),
                domain.startDate(),
                domain.endDate(),
                domain.price(),
                domain.currency()
        );
    }
}
