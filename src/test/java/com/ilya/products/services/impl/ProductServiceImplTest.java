package com.ilya.products.services.impl;

import com.ilya.products.entities.Product;
import com.ilya.products.models.ProductDtoRequest;
import com.ilya.products.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.ilya.products.Utils.ProductsGenerator.generateProduct;
import static com.ilya.products.Utils.ProductsGenerator.generateProductDtoRequest;
import static com.ilya.products.Utils.ProductsGenerator.generateProductDtoRequestWithId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Test
    @DisplayName("Получаем продукт по существующему uuid")
    void getProductById_whenProductExists_shouldProvideProduct() {
        //given
        Product givenProduct = generateProduct();
        when(productRepository.findById(givenProduct.getId()))
                .thenReturn(Optional.of(givenProduct));
        //when
        Product returnProduct = productServiceImpl.getProductById(givenProduct.getId());
        //then
        assertEquals(givenProduct, returnProduct);
    }

    @Test
    @DisplayName("Проверка возникновения исключения если продукт не был найден")
    void getProductById_whenProductNonExists_shouldThrowException() {
        //given
        when(productRepository.findById(any(UUID.class)))
                .thenThrow(new NoSuchElementException("App information about exception"));
        //when
        Throwable thrown = assertThrows(NoSuchElementException.class, () -> {
            productServiceImpl.getProductById(UUID.randomUUID());
        });
        //then
        assertNotNull(thrown.getMessage());
    }

    @Test
    @DisplayName("Проверяет было ли корректно передано UUID для тихого удаления в репозитории")
    void deleteProductById() {
        //given
        Product givenProduct = generateProduct();
        UUID id = givenProduct.getId();
        // when
        productServiceImpl.deleteProductById(id);
        // then
        verify(productRepository).deleteById(id);
    }

    @Test
    @DisplayName("Корректное обновление продукта вместе с обновлением количества и времени, когда количество было обновлено")
    void updateProduct_whenProductIsCorrectAndQuantityWasUpdated_shouldUpdateProductAndLastUpdatedTime() {
        //given
        Product original = generateProduct();
        UUID id = original.getId();
        ProductDtoRequest request = generateProductDtoRequestWithId(id);
        //если в результате генерации одинаковое количество предметов, то обновляем
        if(Objects.equals(request.getQuantity(), original.getQuantity())){
            original.setQuantity(original.getQuantity()+1);
        }
        Product originalCopy = original.clone();

        when(productRepository.findById(id))
                .thenReturn(Optional.of(originalCopy));
        when(productRepository.save(any()))
                .thenAnswer(invocation -> invocation.<Product>getArgument(0));

        // when
        Product updated = productServiceImpl.updateProduct(request);

        // then
        assertNotNull(updated);
        verify(productRepository).save(any());

        assertEquals(request.getId(),         updated.getId());
        assertEquals(request.getTitle(),        updated.getTitle());
        assertEquals(request.getVendorCode(),   updated.getVendorCode());
        assertEquals(request.getDescription(),  updated.getDescription());
        assertEquals(request.getCategory(),     updated.getCategory());
        assertEquals(request.getPrice(),        updated.getPrice());
        assertEquals(request.getQuantity(),     updated.getQuantity());

        assertEquals(original.getCreateAt(),    updated.getCreateAt());
        assertNotEquals(original.getQuantityLastUpdated(), updated.getQuantityLastUpdated());
    }

    @Test
    @DisplayName("Проверка обновления продукта без количества (время последнего обновления количества не должно поменяться")
    void updateProduct_whenProductIsCorrectAndQuantityWasNotUpdated_shouldUpdateProductWithoutTime() {
        //given
        Product original = generateProduct();
        UUID id = original.getId();
        ProductDtoRequest request = generateProductDtoRequestWithId(id);
        //если в результате генерации НЕ одинаковое количество предметов, то делаем равным
        if(!Objects.equals(request.getQuantity(), original.getQuantity())){
            original.setQuantity(request.getQuantity());
        }

        Product originalCopy = original.clone();
        when(productRepository.findById(id))
                .thenReturn(Optional.of(originalCopy));
        when(productRepository.save(any()))
                .thenAnswer(invocation -> invocation.<Product>getArgument(0));

        // when
        Product updated = productServiceImpl.updateProduct(request);

        // then
        assertNotNull(updated);
        verify(productRepository).save(any());

        assertEquals(request.getId(),         updated.getId());
        assertEquals(request.getTitle(),        updated.getTitle());
        assertEquals(request.getVendorCode(),   updated.getVendorCode());
        assertEquals(request.getDescription(),  updated.getDescription());
        assertEquals(request.getCategory(),     updated.getCategory());
        assertEquals(request.getPrice(),        updated.getPrice());
        assertEquals(request.getQuantity(),     updated.getQuantity());

        assertEquals(original.getCreateAt(),    updated.getCreateAt());
        assertEquals(original.getQuantityLastUpdated(), updated.getQuantityLastUpdated());
    }

    @Test
    @DisplayName("Сохранение нового продукта")
    void whenSaveNonExistProduct_shouldOk() {
        //given
        ProductDtoRequest request = generateProductDtoRequest();

        when(productRepository.save(any()))
                .thenAnswer(invocation -> invocation.<Product>getArgument(0));
        //when
        Product updated = productServiceImpl.saveProduct(request);
        //then
        assertNotNull(updated);
        verify(productRepository).save(any());

        assertEquals(request.getId(),         updated.getId());
        assertEquals(request.getTitle(),        updated.getTitle());
        assertEquals(request.getVendorCode(),   updated.getVendorCode());
        assertEquals(request.getDescription(),  updated.getDescription());
        assertEquals(request.getCategory(),     updated.getCategory());
        assertEquals(request.getPrice(),        updated.getPrice());
        assertEquals(request.getQuantity(),     updated.getQuantity());
    }
}