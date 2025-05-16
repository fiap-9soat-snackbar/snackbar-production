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
    
    @Test
    void testSetNullStatus() {
        // Arrange
        StatusUpdateRequest request = new StatusUpdateRequest("PRONTO");
        
        // Act
        request.setStatus(null);
        
        // Assert
        assertNull(request.getStatus());
    }
    
    @Test
    void testSetEmptyStatus() {
        // Arrange
        StatusUpdateRequest request = new StatusUpdateRequest();
        String status = "";
        
        // Act
        request.setStatus(status);
        
        // Assert
        assertEquals(status, request.getStatus());
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        StatusUpdateRequest request1 = new StatusUpdateRequest("PRONTO");
        StatusUpdateRequest request2 = new StatusUpdateRequest("PRONTO");
        StatusUpdateRequest request3 = new StatusUpdateRequest("FINALIZADO");
        
        // Assert - equals
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request1, null);
        assertNotEquals(request1, new Object());
        
        // Assert - hashCode
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        StatusUpdateRequest request = new StatusUpdateRequest("PRONTO");
        
        // Act
        String toString = request.toString();
        
        // Assert
        assertTrue(toString.contains("PRONTO"));
        assertTrue(toString.contains("status"));
    }
    
    @Test
    void testConstructorWithNullStatus() {
        // Act
        StatusUpdateRequest request = new StatusUpdateRequest(null);
        
        // Assert
        assertNull(request.getStatus());
    }
    
    @Test
    void testEqualsWithSameObject() {
        // Arrange
        StatusUpdateRequest request = new StatusUpdateRequest("PRONTO");
        
        // Assert
        assertEquals(request, request);
    }
    
    @Test
    void testEqualsWithDifferentTypes() {
        // Arrange
        StatusUpdateRequest request = new StatusUpdateRequest("PRONTO");
        String notARequest = "PRONTO";
        
        // Assert
        assertNotEquals(request, notARequest);
    }
    
    @Test
    void testEqualsWithNullStatus() {
        // Arrange
        StatusUpdateRequest request1 = new StatusUpdateRequest(null);
        StatusUpdateRequest request2 = new StatusUpdateRequest(null);
        StatusUpdateRequest request3 = new StatusUpdateRequest("PRONTO");
        
        // Assert
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertNotEquals(request3, request1);
    }
}