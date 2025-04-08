package com.snackbar.cooking.domain.exceptions;

// CookingOperationException.java
public class CookingOperationException extends RuntimeException {
    public CookingOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
