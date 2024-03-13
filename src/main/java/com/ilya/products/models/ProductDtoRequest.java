package com.ilya.products.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Сущность, которую отправляет пользователь для обработки товара
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoRequest {

    @JsonProperty("id")
    private UUID id;

    @NotNull(message = "Title code can't be null")
    @Size(min = 1, max = 50, message = "Title size should range from 1 to 50")
    @Schema(name = "title", example = "Желтая уточка",
            description = "Название товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("title")
    private String title;

    @NotNull(message = "Vendor code can't be null")
    @Pattern(regexp = "^\\d{6}$", message = "Vendor code must contain only 6 digits")
    @Schema(name = "vendor_code", example = "110075",
            description = "Артикул", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("vendor_code")
    private String vendorCode;

    @NotNull(message = "Description code can't be null")
    @Size(max = 255, message = "Description too large")
    @Schema(name = "description", example = "Яркая утка для ванны из желтого пластика с полостью внутри.",
            description = "Описание товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Category can't be null")
    @Size(min = 1, max = 50, message = "Category size should range from 1 to 50")
    @Schema(name = "category", example = "Товары для дома",
            description = "Категория товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("category")
    private String category;

    @NotNull(message = "Price can't be null")
    @PositiveOrZero(message = "Price must be positive number or 0")
    @Schema(name = "price", example = "149.99",
            description = "Цена товара в рублях (условно)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("price")
    private Double price;

    @NotNull(message = "Quantity can't be null")
    @PositiveOrZero(message = "Quantity must be positive number or 0")
    @Schema(name = "quantity", example = "15",
            description = "Количество товара в штуках", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("quantity")
    private Integer quantity;

    @JsonCreator
    public ProductDtoRequest(String title, String vendorCode,
                             String description, String category,
                             Double price, Integer quantity) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.vendorCode = vendorCode;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

}

