package com.arqdem.ecommerce.infrastructure.adapter.in.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the price endpoint covering the five scenarios from the problem statement.
 * The full Spring context is loaded with the H2 in-memory database seeded by data.sql.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    private static final String URL = "/api/v1/prices";
    private static final String PRODUCT_ID = "35455";
    private static final String BRAND_ID = "1";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test 1 – 14th June at 10:00 → price list 1 (35.50 EUR)")
    void june14At10am_returnsBasePrice() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 2 – 14th June at 16:00 → price list 2 (25.45 EUR), higher priority wins")
    void june14At16pm_returnsHigherPriorityPrice() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 3 – 14th June at 21:00 → price list 1 (35.50 EUR), promotional window closed")
    void june14At21pm_returnsBasePrice() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 4 – 15th June at 10:00 → price list 3 (30.50 EUR), higher priority wins")
    void june15At10am_returnsPromotionalPrice() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Test 5 – 16th June at 21:00 → price list 4 (38.95 EUR), higher priority wins")
    void june16At21pm_returnsLateSeasonPrice() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("Returns 404 when no price exists for the given parameters")
    void unknownProduct_returns404() throws Exception {
        mockMvc.perform(get(URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "99999")
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Returns 400 when a required parameter is missing")
    void missingParameter_returns400() throws Exception {
        mockMvc.perform(get(URL)
                        .param("productId", PRODUCT_ID)
                        .param("brandId", BRAND_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
