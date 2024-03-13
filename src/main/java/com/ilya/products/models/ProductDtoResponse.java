package com.ilya.products.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ilya.products.entities.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;


/**
 * Сущность товара отправляемая сервером для пользователя
 */
@Getter
@AllArgsConstructor
public class ProductDtoResponse {

    @NotNull(message = "UUID can't be null")
    @Schema(name = "id", example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("id")
    private final UUID id;

    @Size(min = 1, max = 50, message = "Title size should range from 1 to 50")
    @Schema(name = "title", example = "Желтая уточка",
            description = "Название товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("title")
    private final String title;

    @NotNull(message = "Vendor code can't be null")
    @Pattern(regexp = "^\\d{6}$", message = "Vendor code must contain only 6 digits")
    @Schema(name = "vendor_code", example = "110075",
            description = "Артикул", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("vendor_code")
    private final String vendorCode;

    @Size(max = 255, message = "Description too large")
    @Schema(name = "description", example = "Яркая утка для ванны из желтого пластика с полостью внутри.",
            description = "Описание товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("description")
    private final String description;

    @NotNull(message = "Category can't be null")
    @Size(min = 1, max = 50, message = "Category size should range from 1 to 50")
    @Schema(name = "category", example = "Товары для дома",
            description = "Категория товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("category")
    private final String category;

    @NotNull(message = "Price can't be null")
    @Schema(name = "price", example = "149.99",
            description = "Цена товара в рублях (условно)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("price")
    private final Double price;

    @PositiveOrZero(message = "Quantity must be positive number or 0")
    @Schema(name = "quantity", example = "15",
            description = "Количество товара в штуках", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("quantity")
    private final Integer quantity;

    @NotNull(message = "create time can't be null")
    @Schema(name = "createAt", example = "2023-02-26T17:32:28Z",
            description = "Дата и время создания товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("createAt")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime createAt;

    @NotNull(message = "quantity last updated time can't be null")
    @Schema(name = "quantityLastUpdated", example = "2023-02-26T17:32:28Z",
            description = "Дата и время обновления количества товара", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("quantityLastUpdated")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime quantityLastUpdated;

    @Valid
    public ProductDtoResponse(UUID id, String title,
                              String vendorCode, String description,
                              String category, Double price, Integer quantity) {
        this.id = id;
        this.title = title;
        this.vendorCode = vendorCode;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.createAt = OffsetDateTime.now();
        this.quantityLastUpdated = OffsetDateTime.of(
                createAt.toLocalDateTime(),
                createAt.getOffset()
        );
    }

    public ProductDtoResponse(Product product) {
        this(
                product.getId(),
                product.getTitle(),
                product.getVendorCode(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getCreateAt(),
                product.getQuantityLastUpdated()
         );
    }

}
