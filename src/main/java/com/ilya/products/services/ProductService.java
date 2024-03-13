package com.ilya.products.services;


import com.ilya.products.entities.Product;
import com.ilya.products.models.ProductDtoRequest;
import jakarta.validation.Valid;

import java.util.UUID;

public interface ProductService {
    Product getProductById(UUID id);

    void deleteProductById(UUID id);

    Product updateProduct(@Valid ProductDtoRequest productReq);

    Product saveProduct(@Valid ProductDtoRequest productReq);
}
