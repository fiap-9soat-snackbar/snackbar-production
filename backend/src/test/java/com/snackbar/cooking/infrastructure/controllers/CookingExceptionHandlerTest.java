package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.domain.exceptions.CookingNotFoundException;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CookingExceptionHandlerTest {

    private CookingExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CookingExceptionHandler();
    }

    @Test
    void handleCookingNotFoundException_shouldReturnNotFoundStatus() {
        // Arrange
        String errorMessage = "Cooking not found";
        CookingNotFoundException exception = new CookingNotFoundException(errorMessage);

        // Act
        ResponseEntity<CookingExceptionHandler.ErrorResponse> response = exceptionHandler.handleCookingNotFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    void handleUserNotFoundException_shouldReturnNotFoundStatus() {
        // Arrange
        String errorMessage = "User not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        // Act
        ResponseEntity<CookingExceptionHandler.ErrorResponse> response = exceptionHandler.handleUserNotFoundException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    void handleCookingOperationException_shouldReturnBadRequestStatus() {
        // Arrange
        String errorMessage = "Invalid operation";
        CookingOperationException exception = new CookingOperationException(errorMessage);

        // Act
        ResponseEntity<CookingExceptionHandler.ErrorResponse> response = exceptionHandler.handleCookingOperationException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    void handleGenericException_shouldReturnInternalServerErrorStatus() {
        // Arrange
        String errorMessage = "Unexpected error";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<CookingExceptionHandler.ErrorResponse> response = exceptionHandler.handleGenericException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    void errorResponse_getterAndSetter_shouldWorkCorrectly() {
        // Arrange
        String initialMessage = "Initial message";
        String updatedMessage = "Updated message";
        CookingExceptionHandler.ErrorResponse errorResponse = new CookingExceptionHandler.ErrorResponse(initialMessage);

        // Assert initial state
        assertEquals(initialMessage, errorResponse.getMessage());

        // Act - update message
        errorResponse.setMessage(updatedMessage);

        // Assert updated state
        assertEquals(updatedMessage, errorResponse.getMessage());
    }
}