package com.bank.account.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


/**
 * Класс ErrorResponse представляет собой ответ об ошибке, который возвращается клиенту
 * в случае возникновения исключений или ошибок в приложении.
 * Поля:
 * - timestamp: время, когда произошла ошибка.
 * - status: статус HTTP, связанный с ошибкой.
 * - message: сообщение об ошибке, описывающее причину.
 */
@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private HttpStatus status;
    private String message;

    /**
     * Конструктор для создания объекта ErrorResponse.
     *
     * @param message сообщение об ошибке, которое будет возвращено клиенту.
     * @param status  статус HTTP, связанный с ошибкой.
     */
    public ErrorResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }
}
