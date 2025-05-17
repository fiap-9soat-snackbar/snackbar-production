package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCookingByOrderIdUseCaseTest {

    @Mock
    private CookingGateway cookingGateway;

    @InjectMocks
    private GetCookingByOrderIdUseCase getCookingByOrderIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_withExistingOrderId_shouldReturnCooking() {
        // Arrange
        String orderId = "order123";
        Cooking expectedCooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        
        when(cookingGateway.findByOrderId(orderId)).thenReturn(expectedCooking);
        
        // Act
        Cooking result = getCookingByOrderIdUseCase.execute(orderId);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedCooking.id(), result.id());
        assertEquals(expectedCooking.orderId(), result.orderId());
        assertEquals(expectedCooking.status(), result.status());
        
        verify(cookingGateway).findByOrderId(orderId);
    }

    @Test
    void execute_withNonExistingOrderId_shouldThrowCookingNotFoundException() {
        // Arrange
        String orderId = "nonExistentOrder";
        
        when(cookingGateway.findByOrderId(orderId)).thenReturn(null);
        
        // Act & Assert
        CookingNotFoundException exception = assertThrows(CookingNotFoundException.class, () -> {
            getCookingByOrderIdUseCase.execute(orderId);
        });
        
        assertEquals("Cooking not found for order ID: " + orderId, exception.getMessage());
        verify(cookingGateway).findByOrderId(orderId);
    }
}