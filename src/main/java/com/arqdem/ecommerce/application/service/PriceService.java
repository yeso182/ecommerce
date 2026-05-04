package com.arqdem.ecommerce.application.service;

import com.arqdem.ecommerce.application.port.in.GetPriceUseCase;
import com.arqdem.ecommerce.application.port.out.PriceRepository;
import com.arqdem.ecommerce.domain.exception.PriceNotFoundException;
import com.arqdem.ecommerce.domain.model.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class PriceService implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * {@inheritDoc}
     * @throws PriceNotFoundException when no price matches the given criteria
     */
    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, applicationDate));
    }
}
