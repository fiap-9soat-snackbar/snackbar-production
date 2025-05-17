package com.snackbar.cooking.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrderStatusInvalidExceptionTest {

    /**
     * Tests that the OrderStatusInvalidException is created with the correct error message.
     * This test verifies that the exception's message is properly set when constructed.
     */
    @Test
    public void testOrderStatusInvalidExceptionMessage() {
        String errorMessage = "Invalid order status";
        OrderStatusInvalidException exception = new OrderStatusInvalidException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    /**
     * Test case for OrderStatusInvalidException constructor with a message.
     * Verifies that the exception is created with the correct message.
     */
    @Test
    public void test_OrderStatusInvalidException_ConstructorWithMessage() {
        String errorMessage = "Invalid order status";
        OrderStatusInvalidException exception = new OrderStatusInvalidException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

}
