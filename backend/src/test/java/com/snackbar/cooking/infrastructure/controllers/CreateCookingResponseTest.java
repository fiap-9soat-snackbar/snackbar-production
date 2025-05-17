package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.domain.entity.StatusOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateCookingResponseTest {

    @Test
    void constructor_shouldInitializeAllFields() {
        // Arrange
        String id = "cooking123";
        String orderId = "order123";
        StatusOrder status = StatusOrder.PREPARACAO;

        // Act
        CreateCookingResponse response = new CreateCookingResponse(id, orderId, status);

        // Assert
        assertEquals(id, response.id());
        assertEquals(orderId, response.orderId());
        assertEquals(status, response.status());
    }

    @Test
    void constructor_withNullValues_shouldAcceptNullValues() {
        // Act
        CreateCookingResponse response = new CreateCookingResponse(null, null, null);

        // Assert
        assertNull(response.id());
        assertNull(response.orderId());
        assertNull(response.status());
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        CreateCookingResponse response1 = new CreateCookingResponse("id1", "order1", StatusOrder.RECEBIDO);
        CreateCookingResponse response2 = new CreateCookingResponse("id1", "order1", StatusOrder.RECEBIDO);

        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void equals_withDifferentValues_shouldNotBeEqual() {
        // Arrange
        CreateCookingResponse response1 = new CreateCookingResponse("id1", "order1", StatusOrder.RECEBIDO);
        CreateCookingResponse response2 = new CreateCookingResponse("id2", "order1", StatusOrder.RECEBIDO);
        CreateCookingResponse response3 = new CreateCookingResponse("id1", "order2", StatusOrder.RECEBIDO);
        CreateCookingResponse response4 = new CreateCookingResponse("id1", "order1", StatusOrder.PREPARACAO);

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
        StatusOrder status = StatusOrder.PREPARACAO;
        CreateCookingResponse response = new CreateCookingResponse(id, orderId, status);

        // Act
        String result = response.toString();

        // Assert
        assertTrue(result.contains(id));
        assertTrue(result.contains(orderId));
        assertTrue(result.contains(status.toString()));
    }
}