package com.ilya.products.controllers.Impl;

import com.ilya.products.controllers.ProductsApi;
import com.ilya.products.entities.Product;
import com.ilya.products.models.ProductDtoRequest;
import com.ilya.products.models.ProductDtoResponse;
import com.ilya.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Реализация CRUD операций над сущностями Product из app.products
 */
@Controller
@RequestMapping("${openapi.openAPI30.base-path:/v1/products}")
@RequiredArgsConstructor
public class ProductsApiController implements ProductsApi {
    private final ProductService productServiceImpl;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoResponse> getProductById(@PathVariable UUID id){
        Product product = productServiceImpl.getProductById(id);
        return ResponseEntity.ok(new ProductDtoResponse(product));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id){
        productServiceImpl.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping()
    public ResponseEntity<ProductDtoResponse> addProduct(
            @RequestBody ProductDtoRequest request
    ){
        Product product = productServiceImpl.saveProduct(request);
        ProductDtoResponse response = new ProductDtoResponse(product);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @PutMapping()
    public ResponseEntity<Void> updateProduct(
            @RequestBody ProductDtoRequest request
    ){
        productServiceImpl.updateProduct(request);
        return ResponseEntity.noContent().build();
    }
}

