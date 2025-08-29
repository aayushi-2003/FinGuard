package com.oracle.exception;



public class HoldingNotFoundException extends RuntimeException {
    public HoldingNotFoundException(Long id) {
        super("Holding not found with id: " + id);
    }

    public HoldingNotFoundException(String message) {
        super(message);
    }
}

