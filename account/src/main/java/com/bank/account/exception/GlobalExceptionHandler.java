package com.bank.account.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Глобальный обработчик исключений для приложения.
 * Этот класс перехватывает исключения, возникающие в приложении, и возвращает
 * соответствующие ответы об ошибках клиенту. Он использует аннотации
 * @ControllerAdvice и @ExceptionHandler для обработки различных типов исключений.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения типа EntityNotFoundException.
     *
     * @param ex исключение, которое было выброшено.
     * @return объект ErrorResponse с сообщением об ошибке и статусом NOT_FOUND.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        log.info(ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает исключения типа ValidationException.
     *
     * @param ex исключение, которое было выброшено.
     * @return объект ErrorResponse с сообщением об ошибке и статусом BAD_REQUEST.
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(ValidationException ex) {
        log.info(ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает все остальные исключения типа RuntimeException.
     *
     * @param ex исключение, которое было выброшено.
     * @return объект ErrorResponse с сообщением об ошибке и статусом INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGlobalException(Exception ex) {
        log.info(ex.getMessage(), ex);
        return new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
