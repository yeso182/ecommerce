package com.arqdem.ecommerce.application.service;

import com.arqdem.ecommerce.application.port.out.PriceRepository;
import com.arqdem.ecommerce.domain.exception.PriceNotFoundException;
import com.arqdem.ecommerce.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    private static final LocalDateTime DATE = LocalDateTime.of(2020, 6, 14, 10, 0);
    private static final Long PRODUCT_ID = 35455L;
    private static final Integer BRAND_ID = 1;

    @Test
    void getApplicablePrice_returnsPrice_whenRepositoryFindsOne() {
        var expected = buildPrice(1, new BigDecimal("35.50"));
        when(priceRepository.findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Optional.of(expected));

        var result = priceService.getApplicablePrice(DATE, PRODUCT_ID, BRAND_ID);

        verify(priceRepository).findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID);
        assertThat(result.productId()).isEqualTo(PRODUCT_ID);
        assertThat(result.brandId()).isEqualTo(BRAND_ID);
        assertThat(result.priceList()).isEqualTo(1);
        assertThat(result.price()).isEqualByComparingTo(new BigDecimal("35.50"));
        assertThat(result.currency()).isEqualTo("EUR");
    }

    @Test
    void getApplicablePrice_throwsPriceNotFoundException_whenNoPriceExists() {
        when(priceRepository.findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> priceService.getApplicablePrice(DATE, PRODUCT_ID, BRAND_ID))
                .isInstanceOf(PriceNotFoundException.class)
                .hasMessageContaining("35455")
                .hasMessageContaining("1")
                .hasMessageContaining(DATE.toString());

        verify(priceRepository).findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID);
    }

    @Test
    void getApplicablePrice_returnsRepositoryResultUnchanged() {
        var expected = buildPrice(2, new BigDecimal("25.45"));
        when(priceRepository.findApplicablePrice(DATE, PRODUCT_ID, BRAND_ID)).thenReturn(Optional.of(expected));

        var result = priceService.getApplicablePrice(DATE, PRODUCT_ID, BRAND_ID);

        assertThat(result).isSameAs(expected);
    }

    private Price buildPrice(Integer priceList, BigDecimal price) {
        return new Price(
                1L,
                BRAND_ID,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                priceList,
                PRODUCT_ID,
                0,
                price,
                "EUR"
        );
    }
}
