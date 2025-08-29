package com.oracle.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oracle.exception.ApplicationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(exception=ApplicationException.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		
	}

}
