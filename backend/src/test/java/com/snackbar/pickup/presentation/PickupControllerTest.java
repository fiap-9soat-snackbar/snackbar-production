package com.snackbar.pickup.presentation;

import com.snackbar.pickup.usecase.DeliveryPickupUseCase;
import com.snackbar.pickup.usecase.IsReadyPickupUseCase;
import com.snackbar.pickup.usecase.NotifyCustomerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PickupControllerTest {

    @Mock
    private NotifyCustomerUseCase notifyCustomerUseCase;

    @Mock
    private DeliveryPickupUseCase deliveryPickupUseCase;

    @Mock
    private IsReadyPickupUseCase isReadyPickupUseCase;

    @InjectMocks
    private PickupController pickupController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void customerNotify_shouldCallUseCaseAndReturnSuccessMessage() {
        // Arrange
        String orderId = "order123";
        doNothing().when(notifyCustomerUseCase).notify(orderId);

        // Act
        String result = pickupController.customerNotify(orderId);

        // Assert
        assertEquals("Cliente notificado - Pedido: " + orderId, result);
        verify(notifyCustomerUseCase).notify(orderId);
    }

    @Test
    void deliveryOrder_shouldCallUseCaseAndReturnSuccessMessage() {
        // Arrange
        String orderId = "order123";
        doNothing().when(deliveryPickupUseCase).delivery(orderId);

        // Act
        String result = pickupController.deliveryOrder(orderId);

        // Assert
        assertEquals("Pedido " + orderId + " está Finalizado!", result);
        verify(deliveryPickupUseCase).delivery(orderId);
    }

    @Test
    void customerNotify_whenExceptionOccurs_shouldPropagateException() {
        // Arrange
        String orderId = "order123";
        IllegalArgumentException expectedException = new IllegalArgumentException("Test exception");
        doThrow(expectedException).when(notifyCustomerUseCase).notify(orderId);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pickupController.customerNotify(orderId);
        });
        
        assertEquals("Test exception", exception.getMessage());
        verify(notifyCustomerUseCase).notify(orderId);
    }

    @Test
    void deliveryOrder_whenExceptionOccurs_shouldPropagateException() {
        // Arrange
        String orderId = "order123";
        IllegalArgumentException expectedException = new IllegalArgumentException("Test exception");
        doThrow(expectedException).when(deliveryPickupUseCase).delivery(orderId);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pickupController.deliveryOrder(orderId);
        });
        
        assertEquals("Test exception", exception.getMessage());
        verify(deliveryPickupUseCase).delivery(orderId);
    }
    
    @Test
    void customerNotify_withEmptyOrderId_shouldThrowException() {
        // Arrange
        String orderId = "";
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pickupController.customerNotify(orderId);
        });
        
        assertEquals("Order ID cannot be empty", exception.getMessage());
        verify(notifyCustomerUseCase, never()).notify(anyString());
    }
    
    @Test
    void deliveryOrder_withEmptyOrderId_shouldThrowException() {
        // Arrange
        String orderId = "";
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pickupController.deliveryOrder(orderId);
        });
        
        assertEquals("Order ID cannot be empty", exception.getMessage());
        verify(deliveryPickupUseCase, never()).delivery(anyString());
    }
    
    @Test
    void isOrderReady_whenOrderIsReady_shouldReturnTrue() {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isReady(orderId)).thenReturn(true);
        
        // Act
        boolean result = pickupController.isOrderReady(orderId);
        
        // Assert
        assertTrue(result);
        verify(isReadyPickupUseCase).isReady(orderId);
    }
    
    @Test
    void isOrderReady_whenOrderIsNotReady_shouldReturnFalse() {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isReady(orderId)).thenReturn(false);
        
        // Act
        boolean result = pickupController.isOrderReady(orderId);
        
        // Assert
        assertFalse(result);
        verify(isReadyPickupUseCase).isReady(orderId);
    }
    
    @Test
    void isOrderDone_whenOrderIsDone_shouldReturnTrue() {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isDone(orderId)).thenReturn(true);
        
        // Act
        boolean result = pickupController.isOrderDone(orderId);
        
        // Assert
        assertTrue(result);
        verify(isReadyPickupUseCase).isDone(orderId);
    }
    
    @Test
    void isOrderDone_whenOrderIsNotDone_shouldReturnFalse() {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isDone(orderId)).thenReturn(false);
        
        // Act
        boolean result = pickupController.isOrderDone(orderId);
        
        // Assert
        assertFalse(result);
        verify(isReadyPickupUseCase).isDone(orderId);
    }
}