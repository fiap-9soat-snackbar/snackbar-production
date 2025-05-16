package com.snackbar.orderintegration.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        OrderResponse response = new OrderResponse();
        String id = "order123";
        String statusOrder = "PRONTO";
        
        // Act
        response.setId(id);
        response.setStatusOrder(statusOrder);
        
        // Assert
        assertEquals(id, response.getId());
        assertEquals(statusOrder, response.getStatusOrder());
    }

    @Test
    void testDefaultValues() {
        // Act
        OrderResponse response = new OrderResponse();
        
        // Assert
        assertNull(response.getId());
        assertNull(response.getStatusOrder());
    }
}