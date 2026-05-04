package com.arqdem.ecommerce.infrastructure.adapter.in.web;

import com.arqdem.ecommerce.application.port.in.GetPriceUseCase;
import com.arqdem.ecommerce.infrastructure.adapter.in.web.dto.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@Tag(name = "Prices", description = "Price query operations")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    /**
     * Returns the single applicable price for a product and brand at the given point in time.
     * When multiple price lists overlap the one with the highest priority wins.
     */
    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Returns the price entry with the highest priority that covers the requested date"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Price found",
                    content = @Content(schema = @Schema(implementation = PriceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Missing or malformed request parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "No price found for the given parameters", content = @Content)
    })
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(description = "Point in time to evaluate (ISO 8601)", example = "2020-06-14T10:00:00", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,

            @Parameter(description = "Product identifier", example = "35455", required = true)
            @RequestParam Long productId,

            @Parameter(description = "Brand identifier", example = "1", required = true)
            @RequestParam Integer brandId
    ) {
        var price = getPriceUseCase.getApplicablePrice(applicationDate, productId, brandId);
        return ResponseEntity.ok(PriceResponse.from(price));
    }
}
