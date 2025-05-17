package com.snackbar.cooking.domain.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CookingOperationExceptionTest {

    /**
     * Tests that the CookingOperationException constructor correctly sets the message and cause.
     * This test verifies that when a CookingOperationException is created with a message and a cause,
     * both are properly stored and can be retrieved.
     */
    @Test
    public void testCookingOperationExceptionWithMessageAndCause() {
        String errorMessage = "Test error message";
        Throwable cause = new IllegalArgumentException("Test cause");

        CookingOperationException exception = new CookingOperationException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    /**
     * Tests that CookingOperationException constructor correctly sets the error message.
     * This test creates a new CookingOperationException with a specific error message
     * and verifies that getMessage() returns the correct message.
     */
    @Test
    public void test_CookingOperationException_MessageConstructor() {
        String errorMessage = "Test cooking operation error";
        CookingOperationException exception = new CookingOperationException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    /**
     * Test case for CookingOperationException constructor with message and cause.
     * Verifies that the exception is created with the correct message and cause.
     */
    @Test
    public void test_CookingOperationException_WithMessageAndCause() {
        String errorMessage = "Cooking operation failed";
        Throwable cause = new IllegalArgumentException("Invalid ingredient");

        CookingOperationException exception = new CookingOperationException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}
