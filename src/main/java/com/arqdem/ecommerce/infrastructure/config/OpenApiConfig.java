package com.arqdem.ecommerce.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.cfg.DateTimeFeature;

@Configuration
public class OpenApiConfig {

    @Bean
    public JsonMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> builder.disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public OpenAPI openApiDefinition() {
        return new OpenAPI()
                .info(new Info()
                        .title("Prices API")
                        .version("1.0.0")
                        .description("E-commerce price query service — returns the applicable tariff for a product and brand at a given point in time"));
    }
}
