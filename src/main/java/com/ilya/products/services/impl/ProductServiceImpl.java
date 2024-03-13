package com.ilya.products.services.impl;

import com.ilya.products.entities.Product;
import com.ilya.products.models.ProductDtoRequest;
import com.ilya.products.repositories.ProductRepository;
import com.ilya.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

/**
 * Реализация Сервисного слоя для Product
 */
@Service
@Validated
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    /**
     * Получает сущность Product из базы данных
     */
    @Override
    public Product getProductById(UUID id){
        return productRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(
                                "Product with this UUID not found"
                ));
    }

    /**
     * Если id было задано верно, то удаляет сущность Product из базы данных, иначе ничего не делает
     */
    @Override
    public void deleteProductById(UUID id){
        productRepository.deleteById(id);
    }

    /**
     * Обновляет уже существующую сущность Product.
     * @param productReq валидную сущность ProductDtoRequest,
     *        иначе будет выброшено исключение {@link  org.springframework.web.bind.MethodArgumentNotValidException}
     * @return обновленный product
     */
    @Override
    public Product updateProduct(@Valid ProductDtoRequest productReq){
        Product product = getProductById(productReq.getId());
        product.setTitle(productReq.getTitle());
        product.setVendorCode(productReq.getVendorCode());
        product.setDescription(productReq.getDescription());
        product.setCategory(productReq.getCategory());
        product.setPrice(productReq.getPrice());
        if(!Objects.equals(product.getQuantity(), productReq.getQuantity())) {
            product.setQuantity(productReq.getQuantity());
            product.setQuantityLastUpdated(OffsetDateTime.now());
        }
        return productRepository.save(product);
    }

    /**
     * Сохраняет сущность Product. Игнорирует Id и присваивает в базе данных новый.
     * @param productReq валидную сущность ProductDtoRequest,
     *        иначе будет выброшено исключение {@link  org.springframework.web.bind.MethodArgumentNotValidException}
     * @return сохраненный product
     */
    @Override
    public Product saveProduct(@Valid ProductDtoRequest productReq){
        Product product = new Product(productReq);
        return productRepository.save(product);
    }
}
