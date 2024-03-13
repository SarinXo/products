package com.ilya.products.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Violation содержит информацию о поле непрошедшем валидацию.
 * fieldName - имя поле, в котором обнаружено невалидное значение
 * message - сообщение о том, какие именно границы были не соблюдены
 */
@Getter
@RequiredArgsConstructor
public class Violation {

    @NotNull(message = "Field can't be null")
    @Schema(name = "violation", example = "name",
            description = "Имя поля", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("fieldName")
    private final String fieldName;

    @NotNull(message = "Message can't be null")
    @Schema(name = "violation", example = "(Field) can't be null",
            description = "Описание проблемы", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("message")
    private final String message;
}
