package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class StartPreparationUseCaseTest {

    @Mock
    private CookingGateway cookingGateway;

    @Mock
    private CookingRepository cookingRepository;

    @Mock
    private OrderGateway orderGateway;

    @InjectMocks
    private StartPreparationUseCase startPreparationUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCooking_withValidReceivedOrder_shouldUpdateStatusToPreparation() {
        // Arrange
        String orderId = "order123";
        Cooking inputCooking = new Cooking(null, orderId, null);
        Order order = new Order(orderId, "RECEBIDO", null, null, null, null, null, null);

        Cooking updatedCooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        when(cookingGateway.updateCookingStatus(eq(orderId), eq(StatusOrder.PREPARACAO))).thenReturn(updatedCooking);
        when(cookingGateway.findByOrderId(orderId)).thenReturn(updatedCooking);
        
        // Act
        Cooking result = startPreparationUseCase.updateCooking(inputCooking);
        
        // Assert
        assertNotNull(result);
        assertEquals("cooking123", result.id());
        assertEquals(orderId, result.orderId());
        assertEquals(StatusOrder.PREPARACAO, result.status());
        
        verify(orderGateway).findById(orderId);
        verify(cookingGateway).updateCookingStatus(eq(orderId), eq(StatusOrder.PREPARACAO));
        verify(cookingGateway).findByOrderId(orderId);
        verify(orderGateway).updateStatus(eq(orderId), eq("PREPARACAO"));
    }

    @Test
    void updateCooking_withNonExistentOrder_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        Cooking inputCooking = new Cooking(null, orderId, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.empty());
        
        // Act & Assert
        CookingOperationException exception = assertThrows(CookingOperationException.class, () -> {
            startPreparationUseCase.updateCooking(inputCooking);
        });
        
        assertEquals("Order not found: " + orderId, exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(cookingGateway, never()).updateCookingStatus(anyString(), any(StatusOrder.class));
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void updateCooking_withInvalidOrderStatus_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        Cooking inputCooking = new Cooking(null, orderId, null);
        Order order = new Order(orderId, "RECEBIDO", null, null, null, null, null, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        
        // Act & Assert
        OrderStatusInvalidException exception = assertThrows(OrderStatusInvalidException.class, () -> {
            startPreparationUseCase.updateCooking(inputCooking);
        });
        
        assertTrue(exception.getMessage().contains("Order must be in RECEBIDO status"));
        verify(orderGateway).findById(orderId);
        verify(cookingGateway, never()).updateCookingStatus(anyString(), any(StatusOrder.class));
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void updateCooking_whenGatewayThrowsException_shouldWrapInCookingOperationException() {
        // Arrange
        String orderId = "order123";
        Cooking inputCooking = new Cooking(null, orderId, null);
        Order order = new Order(orderId, "RECEBIDO", null, null, null, null, null, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        when(cookingGateway.updateCookingStatus(eq(orderId), eq(StatusOrder.PREPARACAO)))
            .thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        CookingOperationException exception = assertThrows(CookingOperationException.class, () -> {
            startPreparationUseCase.updateCooking(inputCooking);
        });
        
        assertEquals("Failed to process cooking order", exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(cookingGateway).updateCookingStatus(eq(orderId), eq(StatusOrder.PREPARACAO));
    }
}