package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReceiveOrderUseCaseTest {

    @Mock
    private OrderGateway orderGateway;

    @InjectMocks
    private ReceiveOrderUseCase receiveOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_withPaidOrder_shouldUpdateStatusAndReturnSuccessMessage() {
        // Arrange
        String orderId = "order123";
        Order order = new Order(orderId, "PAGO", null, null, null, "PAGO", null, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        
        // Act
        String result = receiveOrderUseCase.execute(orderId);
        
        // Assert
        assertEquals("Pedido recebido", result);
        verify(orderGateway).findById(orderId);
        verify(orderGateway).updateStatus(orderId, "RECEBIDO");
    }

    @Test
    void execute_withNonPaidOrder_shouldNotUpdateStatusAndReturnInfoMessage() {
        // Arrange
        String orderId = "order123";
        String currentStatus = "EM_PREPARO";
        Order order = new Order(orderId, currentStatus, null, null, null, currentStatus, null, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        
        // Act
        String result = receiveOrderUseCase.execute(orderId);
        
        // Assert
        assertEquals("The order is already in " + currentStatus + " status", result);
        verify(orderGateway).findById(orderId);
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void execute_withNonExistentOrder_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.empty());
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            receiveOrderUseCase.execute(orderId);
        });
        
        assertEquals("Order not found", exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void execute_whenGatewayThrowsException_shouldPropagateException() {
        // Arrange
        String orderId = "order123";
        
        when(orderGateway.findById(orderId)).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            receiveOrderUseCase.execute(orderId);
        });
        
        assertEquals("Database error", exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(orderGateway, never()).updateStatus(anyString(), anyString());
    }

    @Test
    void execute_whenUpdateStatusThrowsException_shouldPropagateException() {
        // Arrange
        String orderId = "order123";
        Order order = new Order(orderId, "PAGO", null, null, null, "PAGO", null, null);
        
        when(orderGateway.findById(orderId)).thenReturn(Optional.of(order));
        doThrow(new RuntimeException("Update error")).when(orderGateway).updateStatus(eq(orderId), anyString());
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            receiveOrderUseCase.execute(orderId);
        });
        
        assertEquals("Update error", exception.getMessage());
        verify(orderGateway).findById(orderId);
        verify(orderGateway).updateStatus(orderId, "RECEBIDO");
    }
}