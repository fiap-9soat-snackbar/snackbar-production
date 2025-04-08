package com.snackbar.cooking.domain.exceptions;

public class OrderUpdateException extends RuntimeException {
    public OrderUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
