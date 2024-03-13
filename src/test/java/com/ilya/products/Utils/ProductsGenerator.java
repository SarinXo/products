package com.ilya.products.Utils;


import com.ilya.products.entities.Product;
import com.ilya.products.models.ProductDtoRequest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class ProductsGenerator {

    private static final String[] titles = {"Ручка", "Шарик", "Валик", "Дудка", "Утка", "Жук", "Лук"};
    private static final String[] vendorCodes = {"110075", "110076", "110077", "122277"};
    private static final String[] descriptions = {"пишет", "катается", "что-то делает"};
    private static final String[] categories = {"категория А", "Категория Т", "Категория Д"};
    private static final Double[] prices = {10.0, 20.2, 30.0, 41.1};
    private static final Integer[] quantities = {5, 10, 11, 13, 15};

    public static Product generateProductWithId(UUID uuid) {

        OffsetDateTime createAt =
                OffsetDateTime.of(1, 2, 4,
                        6, 7, 8, 9, ZoneOffset.MIN);
        OffsetDateTime quantityLastUpdated = OffsetDateTime.of(
                createAt.toLocalDateTime(),
                createAt.getOffset()
        );

        return Product.builder()
                .id(uuid)
                .title(randomOf(titles))
                .vendorCode(randomOf(vendorCodes))
                .description(randomOf(descriptions))
                .category(randomOf(categories))
                .price(randomOf(prices))
                .quantity(randomOf(quantities))
                .createAt(createAt)
                .quantityLastUpdated(quantityLastUpdated)
                .build();
    }

    public static Product generateProduct(){
        return generateProductWithId(UUID.randomUUID());
    }

    public static ProductDtoRequest generateProductDtoRequestWithId(UUID uuid){
        return ProductDtoRequest.builder()
                .id(uuid)
                .title(randomOf(titles))
                .vendorCode(randomOf(vendorCodes))
                .description(randomOf(descriptions))
                .category(randomOf(categories))
                .price(randomOf(prices))
                .quantity(randomOf(quantities))
                .build();
    }

    public static ProductDtoRequest generateProductDtoRequest(){
        return generateProductDtoRequestWithId(UUID.randomUUID());
    }

    private static <T> T randomOf(T[] array) {
        int index = (int)(Math.random() * array.length);
        return array[index];
    }
}
