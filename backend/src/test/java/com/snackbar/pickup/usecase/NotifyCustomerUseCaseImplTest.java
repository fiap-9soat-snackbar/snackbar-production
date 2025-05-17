package com.snackbar.pickup.usecase;

import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NotifyCustomerUseCaseImplTest {

    @Mock
    private PickupRepository pickupRepository;

    @Mock
    private OrderClient orderClient;

    @InjectMocks
    private NotifyCustomerUseCaseImpl notifyCustomerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void notify_shouldCreatePickupAndUpdateOrderStatus() {
        // Arrange
        String orderId = "order123";
        Pickup savedPickup = new Pickup();
        savedPickup.setId("pickup123");
        savedPickup.setOrderId(orderId);
        savedPickup.setStatusPickup(StatusPickup.PRONTO);
        savedPickup.setNotifyCustomer(true);

        when(pickupRepository.save(any(Pickup.class))).thenReturn(savedPickup);

        // Act
        notifyCustomerUseCase.notify(orderId);

        // Assert
        ArgumentCaptor<Pickup> pickupCaptor = ArgumentCaptor.forClass(Pickup.class);
        verify(pickupRepository).save(pickupCaptor.capture());
        
        Pickup capturedPickup = pickupCaptor.getValue();
        assertEquals(orderId, capturedPickup.getOrderId());
        assertEquals(StatusPickup.PRONTO, capturedPickup.getStatusPickup());
        assertTrue(capturedPickup.isNotifyCustomer());

        ArgumentCaptor<StatusUpdateRequest> statusCaptor = ArgumentCaptor.forClass(StatusUpdateRequest.class);
        verify(orderClient).updateOrderStatus(eq(orderId), statusCaptor.capture());
        assertEquals("PRONTO", statusCaptor.getValue().getStatus());
    }
    
    @Test
    void notify_withNullOrderId_shouldThrowException() {
        // Arrange
        String orderId = null;
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            notifyCustomerUseCase.notify(orderId);
        });
        
        assertEquals("Order ID cannot be null or empty", exception.getMessage());
        verify(pickupRepository, never()).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void notify_withEmptyOrderId_shouldThrowException() {
        // Arrange
        String orderId = "";
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            notifyCustomerUseCase.notify(orderId);
        });
        
        assertEquals("Order ID cannot be null or empty", exception.getMessage());
        verify(pickupRepository, never()).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void notify_whenRepositorySaveFails_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        when(pickupRepository.save(any(Pickup.class))).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            notifyCustomerUseCase.notify(orderId);
        });
        
        assertEquals("Database error", exception.getMessage());
        verify(pickupRepository).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void notify_whenOrderClientFails_shouldStillSavePickupButPropagateException() {
        // Arrange
        String orderId = "order123";
        Pickup savedPickup = new Pickup();
        savedPickup.setId("pickup123");
        savedPickup.setOrderId(orderId);
        savedPickup.setStatusPickup(StatusPickup.PRONTO);
        savedPickup.setNotifyCustomer(true);
        
        when(pickupRepository.save(any(Pickup.class))).thenReturn(savedPickup);
        doThrow(new RestClientException("API error")).when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act & Assert
        Exception exception = assertThrows(RestClientException.class, () -> {
            notifyCustomerUseCase.notify(orderId);
        });
        
        assertEquals("API error", exception.getMessage());
        verify(pickupRepository).save(any(Pickup.class));
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void notify_withExistingPickup_shouldUpdatePickupAndOrderStatus() {
        // Arrange
        String orderId = "order123";
        Pickup existingPickup = new Pickup();
        existingPickup.setId("pickup123");
        existingPickup.setOrderId(orderId);
        existingPickup.setStatusPickup(StatusPickup.RECEBIDO);
        existingPickup.setNotifyCustomer(false);
        
        Pickup updatedPickup = new Pickup();
        updatedPickup.setId("pickup123");
        updatedPickup.setOrderId(orderId);
        updatedPickup.setStatusPickup(StatusPickup.PRONTO);
        updatedPickup.setNotifyCustomer(true);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(existingPickup));
        when(pickupRepository.save(any(Pickup.class))).thenReturn(updatedPickup);
        
        // Act
        notifyCustomerUseCase.notify(orderId);
        
        // Assert
        ArgumentCaptor<Pickup> pickupCaptor = ArgumentCaptor.forClass(Pickup.class);
        verify(pickupRepository).save(pickupCaptor.capture());
        
        Pickup capturedPickup = pickupCaptor.getValue();
        assertEquals(orderId, capturedPickup.getOrderId());
        assertEquals(StatusPickup.PRONTO, capturedPickup.getStatusPickup());
        assertTrue(capturedPickup.isNotifyCustomer());
        
        ArgumentCaptor<StatusUpdateRequest> statusCaptor = ArgumentCaptor.forClass(StatusUpdateRequest.class);
        verify(orderClient).updateOrderStatus(eq(orderId), statusCaptor.capture());
        assertEquals("PRONTO", statusCaptor.getValue().getStatus());
    }
}