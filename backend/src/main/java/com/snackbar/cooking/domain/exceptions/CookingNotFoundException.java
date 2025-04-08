package com.snackbar.cooking.domain.exceptions;

public class CookingNotFoundException extends RuntimeException {
    public CookingNotFoundException(String message) {
        super(message);
    }
}