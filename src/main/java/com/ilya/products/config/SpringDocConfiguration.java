package com.ilya.products.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация документации
 */
@Configuration
public class SpringDocConfiguration {

    /**
     * Инициализация bean OpenAPI спецификации
     * @return OpenAPI bean
     */
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Склад товаров - OpenAPI 3.0")
                                .description("CRUD операции над товарами")
                                .version("1.0.11")
                );
    }

}
