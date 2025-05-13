package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookingDTOMapperTest {

    private CookingDTOMapper cookingDTOMapper;

    @BeforeEach
    void setUp() {
        cookingDTOMapper = new CookingDTOMapper();
    }

    @Test
    void createRequestToDomain_shouldMapOrderIdCorrectly() {
        // Arrange
        String orderId = "order123";
        
        // Act
        Cooking result = cookingDTOMapper.createRequestToDomain(orderId);
        
        // Assert
        assertNotNull(result);
        assertNull(result.id());
        assertEquals(orderId, result.orderId());
        assertNull(result.status());
    }

    @Test
    void createToResponse_shouldMapAllFieldsCorrectly() {
        // Arrange
        String id = "cooking123";
        String orderId = "order123";
        StatusOrder status = StatusOrder.PREPARACAO;
        
        Cooking cooking = new Cooking(id, orderId, status);
        
        // Act
        CreateCookingResponse result = cookingDTOMapper.createToResponse(cooking);
        
        // Assert
        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals(orderId, result.orderId());
        assertEquals(status, result.status());
    }
}