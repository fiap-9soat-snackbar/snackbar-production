package com.snackbar.orderintegration.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatusUpdateRequestTest {

    @Test
    void testDefaultConstructor() {
        // Act
        StatusUpdateRequest request = new StatusUpdateRequest();
        
        // Assert
        assertNull(request.getStatus());
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String status = "FINALIZADO";
        
        // Act
        StatusUpdateRequest request = new StatusUpdateRequest(status);
        
        // Assert
        assertEquals(status, request.getStatus());
    }

    @Test
    void testSetStatus() {
        // Arrange
        StatusUpdateRequest request = new StatusUpdateRequest();
        String status = "PRONTO";
        
        // Act
        request.setStatus(status);
        
        // Assert
        assertEquals(status, request.getStatus());
    }
}