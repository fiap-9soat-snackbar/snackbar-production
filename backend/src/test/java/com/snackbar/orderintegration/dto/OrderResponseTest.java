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
    
    @Test
    void testSetNullValues() {
        // Arrange
        OrderResponse response = new OrderResponse();
        response.setId("order123");
        response.setStatusOrder("PRONTO");
        
        // Act
        response.setId(null);
        response.setStatusOrder(null);
        
        // Assert
        assertNull(response.getId());
        assertNull(response.getStatusOrder());
    }
    
    @Test
    void testSetEmptyValues() {
        // Arrange
        OrderResponse response = new OrderResponse();
        
        // Act
        response.setId("");
        response.setStatusOrder("");
        
        // Assert
        assertEquals("", response.getId());
        assertEquals("", response.getStatusOrder());
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        OrderResponse response1 = new OrderResponse();
        response1.setId("order123");
        response1.setStatusOrder("PRONTO");
        
        OrderResponse response2 = new OrderResponse();
        response2.setId("order123");
        response2.setStatusOrder("PRONTO");
        
        OrderResponse response3 = new OrderResponse();
        response3.setId("order456");
        response3.setStatusOrder("PRONTO");
        
        OrderResponse response4 = new OrderResponse();
        response4.setId("order123");
        response4.setStatusOrder("FINALIZADO");
        
        // Assert - equals
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response1, response4);
        assertNotEquals(response1, null);
        assertNotEquals(response1, new Object());
        
        // Assert - hashCode
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        assertNotEquals(response1.hashCode(), response4.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        OrderResponse response = new OrderResponse();
        response.setId("order123");
        response.setStatusOrder("PRONTO");
        
        // Act
        String toString = response.toString();
        
        // Assert
        assertTrue(toString.contains("order123"));
        assertTrue(toString.contains("PRONTO"));
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("statusOrder"));
    }
    
    @Test
    void testEqualsWithSameObject() {
        // Arrange
        OrderResponse response = new OrderResponse();
        response.setId("order123");
        response.setStatusOrder("PRONTO");
        
        // Assert
        assertEquals(response, response);
    }
    
    @Test
    void testEqualsWithDifferentTypes() {
        // Arrange
        OrderResponse response = new OrderResponse();
        response.setId("order123");
        String notAResponse = "order123";
        
        // Assert
        assertNotEquals(response, notAResponse);
    }
    
    @Test
    void testEqualsWithNullFields() {
        // Arrange
        OrderResponse response1 = new OrderResponse();
        OrderResponse response2 = new OrderResponse();
        
        OrderResponse response3 = new OrderResponse();
        response3.setId("order123");
        
        OrderResponse response4 = new OrderResponse();
        response4.setStatusOrder("PRONTO");
        
        // Assert
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response1, response4);
        assertNotEquals(response3, response4);
    }
    
    @Test
    void testEqualsWithOneNullField() {
        // Arrange
        OrderResponse response1 = new OrderResponse();
        response1.setId("order123");
        response1.setStatusOrder(null);
        
        OrderResponse response2 = new OrderResponse();
        response2.setId("order123");
        response2.setStatusOrder(null);
        
        OrderResponse response3 = new OrderResponse();
        response3.setId("order123");
        response3.setStatusOrder("PRONTO");
        
        // Assert
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response3, response1);
    }
    
    @Test
    void testCopyConstructor() {
        // Arrange
        OrderResponse original = new OrderResponse();
        original.setId("order123");
        original.setStatusOrder("PRONTO");
        
        // Act
        OrderResponse copy = new OrderResponse(original);
        
        // Assert
        assertEquals(original.getId(), copy.getId());
        assertEquals(original.getStatusOrder(), copy.getStatusOrder());
        assertEquals(original, copy);
    }
}