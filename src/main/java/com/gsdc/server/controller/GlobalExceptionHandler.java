package com.gsdc.server.controller;

import com.gsdc.server.dto.error.ErrorResponse;
import com.gsdc.server.exceptions.AbstractException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<Object> handleItemNotFoundException(AbstractException exception) {
        final ErrorResponse responseError =
                new ErrorResponse(exception.getRawStatusCode(), exception.getMessage(), exception.getShow());

        return ResponseEntity.status(exception.getRawStatusCode()).body(responseError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        int statusCode = HttpStatus.BAD_REQUEST.value();
        String message = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        String stackTrace = exception.getMessage();
        final ErrorResponse responseError = new ErrorResponse(statusCode, message, true, stackTrace);

        return ResponseEntity.status(statusCode).body(responseError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorException(Exception exception) {
        final String serverErrorMessage = "Oops, something went wrong, please try later!";
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        final ErrorResponse responseError = new ErrorResponse(statusCode, serverErrorMessage, true, exception.getMessage());

        return ResponseEntity.status(statusCode).body(responseError);
    }

}
