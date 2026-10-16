package com.example.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, String>> handleApplicationException(
            ApplicationException exception) {
        HttpStatus status = switch (exception.getErrorCase()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case INVALID_OPERATION -> HttpStatus.BAD_REQUEST;
        };

        Map<String, String> error = new LinkedHashMap<>();
        error.put("errorCase", exception.getErrorCase().name());
        error.put("message", exception.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
