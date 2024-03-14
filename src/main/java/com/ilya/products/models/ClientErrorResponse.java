package com.ilya.products.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

/**
 * ClientError возвращается пользователю в качестве информации об ошибке.
 */
@Getter
@Schema(name = "ClientErrorResponse", description = "В случае ожидаемой ошибки будет отослан этот объект")
public class ClientErrorResponse {

    /**
     * Краткое описание проблемы
     */
    @NotNull(message = "Title can't be null")
    @Schema(name = "title", example = "NoSuchElementException",
            description = "Краткое описание проблемы", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("title")
    private final String title;

    /**
     * Конкретное описание возникшей ошибки
     */
    @NotNull(message = "Details can't be null")
    @Schema(name = "details", example = "Product with UUID=66c8fae2-0551-4d90-9eeb-dc24eb683bb8 not found",
            description = "Конкретное описание возникшей ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("details")
    private final String details;

    /**
     * Метод и URL запроса
     */
    @NotNull(message = "Request can't be null")
    @Schema(name = "request", example = "GET /v1/products/66c8fae2-0551-4d90-9eeb-dc24eb683bb8",
            description = "Метод и URL запроса", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("request")
    private final String request;

    /**
     * Дата и время возникновения ошибки
     */
    @NotNull(message = "Timestamp can't be null")
    @Schema(name = "timestamp", example = "2025-07-26T17:32:28Z",
            description = "Дата и время возникновения ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("timestamp")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime timestamp;

    /**
     * Дефолтный конструктор. Время возникновения ошибки инициализируется при создании
     * @param title ошибка или краткое описание ошибки
     * @param details информация о возникновении ошибки
     * @param request путь и метод по которому была вызвана ошибка
     */
    public ClientErrorResponse(String title, String details, String request) {
        this.title = title;
        this.details = details;
        this.request = request;
        this.timestamp = OffsetDateTime.now();
    }

}