package com.oracle.exception;



public class PortfolioNotFoundException extends RuntimeException {
    public PortfolioNotFoundException(Long id) {
        super("Portfolio not found with id: " + id);
    }

    public PortfolioNotFoundException(String message) {
        super(message);
    }
}
