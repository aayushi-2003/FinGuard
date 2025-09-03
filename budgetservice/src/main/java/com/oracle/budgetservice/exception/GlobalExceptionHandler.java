package com.oracle.budgetservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
//        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }

    // Handle all RuntimeExceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRuntimeException(Exception ex) {
    	System.out.println("Error received");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE);
    }
}