package com.ilya.products.controllers;

import com.ilya.products.models.ClientErrorResponse;
import com.ilya.products.models.ProductDtoRequest;
import com.ilya.products.models.ProductDtoResponse;
import com.ilya.products.models.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Validated
@Tag(name = "Products", description = "Реализация CRUD операций над товарами")
public interface ProductsApi {

    /**
     * POST /products : Добавляет новый товар.
     * Добавляет новую сущность товара в базу данных.
     *
     * @param productDtoRequest ProductDtoRequest содержащий все поля кроме id (Может содержать, но будет проигнорировано) (required)
     * @return Успешно добавлено (status code 201)
     *         or Введенные данные некорректны (status code 400)
     *         or Формат данных корректен, но сами данные содержат конфликт (status code 409)
     */
    @Operation(
            operationId = "addProduct",
            summary = "Добавляет новый товар",
            description = "Добавляет новую сущность товара в базу данных",
            tags = { "Products" },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешно добавлено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDtoResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Введенные данные некорректны", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Формат данных корректен, но сами данные содержат конфликт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ClientErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/products",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<ProductDtoResponse> addProduct(
            @Parameter(name = "ProductDto",
                    description = "ProductDtoRequest содержащий все поля кроме id (Может содержать, но будет проигнорировано)",
                    required = true)
            @Valid @RequestBody ProductDtoRequest productDtoRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    /**
     * DELETE /products/{id} : Удаляет товар по UUID.
     * Производит полное удаление сущности из базы данных.
     *
     * @param id UUID товара который мы хотим удалить (required)
     * @return Успешно удалено (status code 204)
     *         or UUID неправильно введен (status code 400)
     *         or Товар с вашим UUID не найден (status code 404)
     */
    @Operation(
            operationId = "deleteProductByUuid",
            summary = "Удаляет товар по UUID",
            description = "Производит полное удаление сущности из базы данных",
            tags = { "Products" },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Успешно удалено")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/products/{id}"
    )
    default ResponseEntity<Void> deleteProductById(
            @Parameter(name = "id", description = "UUID товара который мы хотим получить",
                    required = true, in = ParameterIn.PATH)
            @PathVariable("id") UUID id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    /**
     * GET /products/{id} : Получает товар по UUID.
     * Получает всю информацию о сущности товара из базы данных по UUID.
     *
     * @param id UUID товара который мы хотим получить (required)
     * @return Успешно получено (status code 200)
     *         or UUID неправильно введен (status code 400)
     *         or Товар с вашим UUID не найден (status code 404)
     */
    @Operation(
            operationId = "getProductByUuid",
            summary = "Получает товар по UUID",
            description = "Получает всю информацию о сущности товара из базы данных по UUID",
            tags = { "Products" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDtoResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "UUID неправильно введен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ClientErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Товар с вашим UUID не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ClientErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/products/{id}",
            produces = { "application/json" }
    )
    default ResponseEntity<ProductDtoResponse> getProductById(
            @Parameter(name = "id", description = "UUID товара который мы хотим получить",
                    required = true, in = ParameterIn.PATH)
            @PathVariable("id") UUID id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    /**
     * PUT /products : Обновляет существующий товар.
     * Заменяет поля из productDtoRequest в базе данных на соответствующие поля из ProductDto.
     *
     * @param productDtoRequest ProductDtoRequest содержащий все поля (required)
     * @return Успешно обновлено (status code 204)
     *         or Введенные данные некорректны (status code 400)
     *         or Товар с вашим UUID не найден (status code 404)
     *         or Формат данных корректен, но сами данные содержат конфликт (status code 409)
     */
    @Operation(
            operationId = "updateProduct",
            summary = "Обновляет существующий товар",
            description = "Заменяет поля из ProductEntity в базе данных на соответствующие поля из  ProductDto",
            tags = { "Products" },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Успешно обновлено"),
                    @ApiResponse(responseCode = "400", description = "Введенные данные некорректны", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Товар с вашим UUID не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ClientErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Формат данных корректен, но сами данные содержат конфликт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ClientErrorResponse.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/products",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    default ResponseEntity<Void> updateProduct(
            @Parameter(name = "ProductDtoRequest", description = "ProductDtoRequest содержащий все поля", required = true)
            @Valid @RequestBody ProductDtoRequest productDtoRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}