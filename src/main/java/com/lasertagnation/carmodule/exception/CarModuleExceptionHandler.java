package com.lasertagnation.carmodule.exception;

import org.hibernate.LazyInitializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Scoped advice for CarModule REST controllers — keeps entity graphs inside services but still maps common Hibernate
 * misuse when someone refactors controllers later.
 */
@RestControllerAdvice(basePackages = "com.lasertagnation.carmodule.controller")
public class CarModuleExceptionHandler {

    @ExceptionHandler(LazyInitializationException.class)
    public ResponseEntity<Map<String, Object>> handleLazyInit(LazyInitializationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("error", "LazyInitializationException");
        body.put("message", "Association accessed outside a session/transaction — keep reads inside @Transactional service methods and return DTOs.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
