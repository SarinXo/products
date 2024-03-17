package com.ilya.products.handlers;

import com.ilya.products.models.ClientErrorResponse;
import com.ilya.products.models.ValidationErrorResponse;
import com.ilya.products.models.Violation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Глобальный обработчик ошибок. Перехватватывает ошибки следующих классов:
 *      <p>{@link NoSuchElementException}
 *      <p>{@link MethodArgumentTypeMismatchException}
 *      <p>{@link HttpMessageNotReadableException}
 *      <p>{@link DataIntegrityViolationException}
 *      <p>{@link ConstraintViolationException}
 *      <p>{@link MethodArgumentNotValidException}
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String VALIDATION_TITLE = "Validation error";

    private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = new HashMap<>(){{
        put(NoSuchElementException.class, HttpStatus.NOT_FOUND);
        put(MethodArgumentTypeMismatchException.class, HttpStatus.BAD_REQUEST);
        put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
        put(DataIntegrityViolationException.class, HttpStatus.CONFLICT);
    }};

    /**
     * Обрабатывает общие исключения, определяя соответствующие HTTP-статусы и создавая объект {@code ClientErrorResponse} для
     *  * единообразного формата ответа.
     * @param e - Сама ошибка
     * @param request - Запрос в котором была обнаружена ошибка
     * @param method - HTTP метод в котором была обнаружена ошибка
     * @return Заголовок ошибки, детали ошибки, путь и метод по которому была вызвана ошибка, время возникновения ошибки
     */
    @ResponseBody
    @ExceptionHandler({
            NoSuchElementException.class,
            MethodArgumentTypeMismatchException.class,
            DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ClientErrorResponse> defaultHandle(
            Throwable e, HttpServletRequest request, HttpMethod method
    ) {
        HttpStatus status = determineHttpStatus(e);
        String title = e.getClass().getSimpleName();
        String details = e.getMessage();

        return ResponseEntity.status(status).body(
                new ClientErrorResponse(title, details, requestInfo(request, method))
        );
    }

    /**
     * Сопоставляет ошибку статусному HTTP коду из EXCEPTION_STATUS_MAP, если в map нет информации об ошибке, то возвращает 500 код
     * @param e Ошибка обрабатываемая методом
     * @return HTTP статус в виде {@link HttpStatus}
     */
    private HttpStatus determineHttpStatus(Throwable e) {
        return EXCEPTION_STATUS_MAP.getOrDefault(e.getClass(), INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработка ошибок валидации, если они дошли до репозиториев
     * @param e - Сама ошибка
     * @param request - Запрос в котором была обнаружена ошибка
     * @param method - HTTP метод в котором была обнаружена ошибка
     * @return Заголовок ошибки, детали в виде массива из названия поля и сообщения об ошибке в этом поле, путь и метод по которому была вызвана ошибка, время возникновения ошибки
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e, HttpServletRequest request, HttpMethod method
    ) {
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getInvalidValue().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(VALIDATION_TITLE, violations, requestInfo(request, method));
    }

    /**
     * Обработка ошибок валидации в контроллерах и сервисах
     * @param e - Сама ошибка
     * @param request - Запрос в котором была обнаружена ошибка
     * @param method - HTTP метод в котором была обнаружена ошибка
     * @return Заголовок ошибки, детали в виде массива из названия поля и сообщения об ошибке в этом поле, путь и метод по которому была вызвана ошибка, время возникновения ошибки
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request, HttpMethod method
    ) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(
                        error -> new Violation(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(VALIDATION_TITLE, violations, requestInfo(request, method));
    }

    private String requestInfo(HttpServletRequest request, HttpMethod method){
        return method.toString() + " " + request.getRequestURI();
    }

}
