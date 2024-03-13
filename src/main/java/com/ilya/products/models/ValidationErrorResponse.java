package com.ilya.products.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * ValidationError возвращается пользователю в качестве информации об ошибке валидации.
 */
@Getter
public class ValidationErrorResponse {

    @NotNull(message = "Title can't be null")
    @Schema(name = "title", example = "Невалидные данные",
            description = "Краткое описание проблемы", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("title")
    private final String title;
    @NotNull(message = "Details can't be null")
    @Schema(name = "details", example = "ERROR",
            description = "Массив из полей непрошедших валидацию", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("details")
    private final List<Violation> details;

    @NotNull(message = "Request can't be null")
    @Schema(name = "request", example = "POST /products",
            description = "Метод и URL запроса", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("request")
    private final String request;

    @NotNull(message = "Timestamp can't be null")
    @Schema(name = "timestamp", example = "2023-02-26T17:32:28Z",
            description = "Дата и время возникновения ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("timestamp")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final OffsetDateTime timestamp;

    public ValidationErrorResponse(String title, List<Violation> details, String request) {
        this.title = title;
        this.details = details;
        this.request = request;
        this.timestamp = OffsetDateTime.now();
    }
}
