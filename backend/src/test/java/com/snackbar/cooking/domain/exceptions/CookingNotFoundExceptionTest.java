package com.snackbar.cooking.domain.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CookingNotFoundExceptionTest {

    /**
     * Tests that the CookingNotFoundException is correctly instantiated with an empty message.
     * This is a negative test case as it checks the behavior of the exception
     * with an edge case input (empty string).
     */
    @Test
    public void testCookingNotFoundExceptionWithEmptyMessage() {
        CookingNotFoundException exception = new CookingNotFoundException("");
        assertEquals("", exception.getMessage());
    }

    /**
     * Test case for CookingNotFoundException constructor with a message.
     * Verifies that the exception is created with the correct message.
     */
    @Test
    public void test_CookingNotFoundException_CreatesExceptionWithMessage() {
        String errorMessage = "Cooking not found";
        CookingNotFoundException exception = new CookingNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

}
