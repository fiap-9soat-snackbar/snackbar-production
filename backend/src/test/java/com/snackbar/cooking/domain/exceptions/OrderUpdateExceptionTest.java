package com.snackbar.cooking.domain.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderUpdateExceptionTest {

    /**
     * Tests that OrderUpdateException correctly handles an empty message string.
     * This is a negative test case to ensure the exception can be created with an empty message.
     */
    @Test
    public void testOrderUpdateExceptionWithEmptyMessage() {
        String emptyMessage = "";
        OrderUpdateException exception = new OrderUpdateException(emptyMessage, new RuntimeException());
        assertEquals(emptyMessage, exception.getMessage());
        assertNotNull(exception.getCause());
    }

    /**
     * Tests that OrderUpdateException correctly handles null message and cause.
     * This is a negative test case to ensure the exception can be created with null parameters.
     */
    @Test
    public void testOrderUpdateExceptionWithNullMessageAndCause() {
        OrderUpdateException exception = new OrderUpdateException(null, null);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    /**
     * Test case for OrderUpdateException constructor with message and cause
     * 
     * This test verifies that the OrderUpdateException constructor correctly
     * initializes the exception with the provided message and cause.
     */
    @Test
    public void test_OrderUpdateException_WithMessageAndCause() {
        String errorMessage = "Order update failed";
        Throwable cause = new IllegalArgumentException("Invalid order status");

        OrderUpdateException exception = new OrderUpdateException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}
