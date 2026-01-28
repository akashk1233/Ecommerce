package com.ecom.orderservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ListNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleListNotFoundException(ListNotFoundException exception)
    {
        Map<String, Object> body = new HashMap<>();

        body.put("time", LocalDateTime.now());
        body.put("message", exception.getMessage());
        body.put("status", 404);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }
}
