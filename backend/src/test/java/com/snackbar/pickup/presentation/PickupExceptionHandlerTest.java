package com.snackbar.pickup.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PickupExceptionHandlerTest {

    private PickupExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new PickupExceptionHandler();
    }

    @Test
    void handleIllegalArgumentException_shouldReturnBadRequestStatus() {
        // Arrange
        String errorMessage = "Invalid argument";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // Act
        ResponseEntity<PickupExceptionHandler.ErrorResponse> response = exceptionHandler.handleIllegalArgumentException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    void errorResponse_getterAndSetter_shouldWorkCorrectly() {
        // Arrange
        String initialMessage = "Initial message";
        String updatedMessage = "Updated message";
        PickupExceptionHandler.ErrorResponse errorResponse = new PickupExceptionHandler.ErrorResponse(initialMessage);

        // Assert initial state
        assertEquals(initialMessage, errorResponse.getMessage());

        // Act - update message
        errorResponse.setMessage(updatedMessage);

        // Assert updated state
        assertEquals(updatedMessage, errorResponse.getMessage());
    }
}