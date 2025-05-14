package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
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

class CreateCookingUseCaseTest {

    @Mock
    private CookingGateway cookingGateway;

    @Mock
    private OrderGateway orderGateway;

    @InjectMocks
    private CreateCookingUseCase createCookingUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCooking_withValidPaidOrder_shouldCreateCookingAndUpdateOrderStatus() {
        // Arrange
        String orderId = "order123";
        Cooking inputCooking = new Cooking(null, orderId, null);
        Order order = new Order(orderId, "PAGO", null, null, null, null, null, null);
        
        Cooking savedCooking = new Cooking("cooking123", orderId, StatusOrder.RECEBIDO);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        when(cookingGateway.createCooking(any(Cooking.class))).thenReturn(savedCooking);
        
        // Act
        Cooking result = createCookingUseCase.createCooking(inputCooking);
        
        // Assert
        assertNotNull(result);
        assertEquals("cooking123", result.id());
        assertEquals(orderId, result.orderId());
        assertEquals(StatusOrder.RECEBIDO, result.status());
        
        verify(orderGateway).findById(orderId);
        verify(cookingGateway).createCooking(any(Cooking.class));
        verify(orderGateway).updateStatus(eq(orderId), eq("RECEBIDO"));
    }

    @Test
    void createCooking_withNonExistentOrder_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        Cooking inputCooking = new Cooking(null, orderId, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.empty());
        
        // Act & Assert
        CookingOperationException exception = assertThrows(CookingOperationException.class, () -> {
            createCookingUseCase.createCooking(inputCooking);
        });
        
        assertEquals("Order not found: " + orderId, exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(cookingGateway, never()).createCooking(any(Cooking.class));
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void createCooking_withInvalidOrderStatus_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        Cooking inputCooking = new Cooking(null, orderId, null);
        Order order = new Order(orderId, "RECEBIDO", null, null, null, null, null, null);

        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        
        // Act & Assert
        OrderStatusInvalidException exception = assertThrows(OrderStatusInvalidException.class, () -> {
            createCookingUseCase.createCooking(inputCooking);
        });
        
        assertTrue(exception.getMessage().contains("Order must be in PAGO status"));
        verify(orderGateway).findById(orderId);
        verify(cookingGateway, never()).createCooking(any(Cooking.class));
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void createCooking_whenGatewayThrowsException_shouldWrapInCookingOperationException() {
        // Arrange
        String orderId = "order123";
        Cooking inputCooking = new Cooking(null, orderId, null);
        Order order = new Order(orderId, "PAGO", null, null, null, null, null, null);

        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        when(cookingGateway.createCooking(any(Cooking.class))).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        CookingOperationException exception = assertThrows(CookingOperationException.class, () -> {
            createCookingUseCase.createCooking(inputCooking);
        });
        
        assertEquals("Failed to process cooking order", exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(cookingGateway).createCooking(any(Cooking.class));
    }
}