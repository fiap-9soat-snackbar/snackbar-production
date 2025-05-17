package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.domain.entity.StatusOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetCookingResponseTest {

    @Test
    void constructor_shouldInitializeAllFields() {
        // Arrange
        String id = "cooking123";
        String orderId = "order123";
        Enum<?> status = StatusOrder.PREPARACAO;

        // Act
        GetCookingResponse response = new GetCookingResponse(id, orderId, status);

        // Assert
        assertEquals(id, response.id());
        assertEquals(orderId, response.orderId());
        assertEquals(status, response.status());
    }

    @Test
    void constructor_withNullValues_shouldAcceptNullValues() {
        // Act
        GetCookingResponse response = new GetCookingResponse(null, null, null);

        // Assert
        assertNull(response.id());
        assertNull(response.orderId());
        assertNull(response.status());
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        GetCookingResponse response1 = new GetCookingResponse("id1", "order1", StatusOrder.RECEBIDO);
        GetCookingResponse response2 = new GetCookingResponse("id1", "order1", StatusOrder.RECEBIDO);

        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void equals_withDifferentValues_shouldNotBeEqual() {
        // Arrange
        GetCookingResponse response1 = new GetCookingResponse("id1", "order1", StatusOrder.RECEBIDO);
        GetCookingResponse response2 = new GetCookingResponse("id2", "order1", StatusOrder.RECEBIDO);
        GetCookingResponse response3 = new GetCookingResponse("id1", "order2", StatusOrder.RECEBIDO);
        GetCookingResponse response4 = new GetCookingResponse("id1", "order1", StatusOrder.PREPARACAO);

        // Assert
        assertNotEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertNotEquals(response1, response4);
    }

    @Test
    void toString_shouldContainAllFields() {
        // Arrange
        String id = "cooking123";
        String orderId = "order123";
        Enum<?> status = StatusOrder.PREPARACAO;
        GetCookingResponse response = new GetCookingResponse(id, orderId, status);

        // Act
        String result = response.toString();

        // Assert
        assertTrue(result.contains(id));
        assertTrue(result.contains(orderId));
        assertTrue(result.contains(status.toString()));
    }
}