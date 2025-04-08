package com.snackbar.cooking.domain.exceptions;

public class OrderStatusInvalidException extends RuntimeException {
    public OrderStatusInvalidException(String message) {
        super(message);
    }
}