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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DeliveryPickupUseCaseImplTest {

    @Mock
    private PickupRepository pickupRepository;

    @Mock
    private OrderClient orderClient;

    @InjectMocks
    private DeliveryPickupUseCaseImpl deliveryPickupUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void delivery_whenPickupExists_shouldUpdateStatusAndNotifyOrderService() {
        // Arrange
        String orderId = "order123";
        Pickup existingPickup = new Pickup();
        existingPickup.setId("pickup123");
        existingPickup.setOrderId(orderId);
        existingPickup.setStatusPickup(StatusPickup.PRONTO);
        existingPickup.setNotifyCustomer(true);

        Pickup updatedPickup = new Pickup();
        updatedPickup.setId("pickup123");
        updatedPickup.setOrderId(orderId);
        updatedPickup.setStatusPickup(StatusPickup.FINALIZADO);
        updatedPickup.setNotifyCustomer(true);

        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(existingPickup));
        when(pickupRepository.save(any(Pickup.class))).thenReturn(updatedPickup);

        // Act
        deliveryPickupUseCase.delivery(orderId);

        // Assert
        ArgumentCaptor<Pickup> pickupCaptor = ArgumentCaptor.forClass(Pickup.class);
        verify(pickupRepository).save(pickupCaptor.capture());
        
        Pickup capturedPickup = pickupCaptor.getValue();
        assertEquals(orderId, capturedPickup.getOrderId());
        assertEquals(StatusPickup.FINALIZADO, capturedPickup.getStatusPickup());

        ArgumentCaptor<StatusUpdateRequest> statusCaptor = ArgumentCaptor.forClass(StatusUpdateRequest.class);
        verify(orderClient).updateOrderStatus(eq(orderId), statusCaptor.capture());
        assertEquals("FINALIZADO", statusCaptor.getValue().getStatus());
    }

    @Test
    void delivery_whenPickupDoesNotExist_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryPickupUseCase.delivery(orderId);
        });

        assertEquals("Este pedido não foi retirado: " + orderId, exception.getMessage());
        verify(pickupRepository, never()).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void delivery_withNullOrderId_shouldThrowException() {
        // Arrange
        String orderId = null;
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryPickupUseCase.delivery(orderId);
        });
        
        assertEquals("Order ID cannot be null or empty", exception.getMessage());
        verify(pickupRepository, never()).findByOrderId(any());
        verify(pickupRepository, never()).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void delivery_withEmptyOrderId_shouldThrowException() {
        // Arrange
        String orderId = "";
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deliveryPickupUseCase.delivery(orderId);
        });
        
        assertEquals("Order ID cannot be null or empty", exception.getMessage());
        verify(pickupRepository, never()).findByOrderId(any());
        verify(pickupRepository, never()).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void delivery_whenRepositorySaveFails_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        Pickup existingPickup = new Pickup();
        existingPickup.setId("pickup123");
        existingPickup.setOrderId(orderId);
        existingPickup.setStatusPickup(StatusPickup.PRONTO);
        existingPickup.setNotifyCustomer(true);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(existingPickup));
        when(pickupRepository.save(any(Pickup.class))).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deliveryPickupUseCase.delivery(orderId);
        });
        
        assertEquals("Database error", exception.getMessage());
        verify(pickupRepository).findByOrderId(orderId);
        verify(pickupRepository).save(any(Pickup.class));
        verify(orderClient, never()).updateOrderStatus(any(), any());
    }
    
    @Test
    void delivery_whenOrderClientFails_shouldStillSavePickupButPropagateException() {
        // Arrange
        String orderId = "order123";
        Pickup existingPickup = new Pickup();
        existingPickup.setId("pickup123");
        existingPickup.setOrderId(orderId);
        existingPickup.setStatusPickup(StatusPickup.PRONTO);
        existingPickup.setNotifyCustomer(true);
        
        Pickup updatedPickup = new Pickup();
        updatedPickup.setId("pickup123");
        updatedPickup.setOrderId(orderId);
        updatedPickup.setStatusPickup(StatusPickup.FINALIZADO);
        updatedPickup.setNotifyCustomer(true);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(existingPickup));
        when(pickupRepository.save(any(Pickup.class))).thenReturn(updatedPickup);
        doThrow(new RestClientException("API error")).when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act & Assert
        RestClientException exception = assertThrows(RestClientException.class, () -> {
            deliveryPickupUseCase.delivery(orderId);
        });
        
        assertEquals("API error", exception.getMessage());
        verify(pickupRepository).findByOrderId(orderId);
        verify(pickupRepository).save(any(Pickup.class));
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void delivery_whenPickupIsAlreadyFinalized_shouldNotUpdateAndReturnSuccess() {
        // Arrange
        String orderId = "order123";
        Pickup existingPickup = new Pickup();
        existingPickup.setId("pickup123");
        existingPickup.setOrderId(orderId);
        existingPickup.setStatusPickup(StatusPickup.FINALIZADO);
        existingPickup.setNotifyCustomer(true);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(existingPickup));
        
        // Act
        deliveryPickupUseCase.delivery(orderId);
        
        // Assert
        verify(pickupRepository).findByOrderId(orderId);
        verify(pickupRepository).save(any(Pickup.class));
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void delivery_whenPickupIsNotReady_shouldStillUpdateToFinalized() {
        // Arrange
        String orderId = "order123";
        Pickup existingPickup = new Pickup();
        existingPickup.setId("pickup123");
        existingPickup.setOrderId(orderId);
        existingPickup.setStatusPickup(StatusPickup.RECEBIDO); // Not PRONTO
        existingPickup.setNotifyCustomer(false);
        
        Pickup updatedPickup = new Pickup();
        updatedPickup.setId("pickup123");
        updatedPickup.setOrderId(orderId);
        updatedPickup.setStatusPickup(StatusPickup.FINALIZADO);
        updatedPickup.setNotifyCustomer(false);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(existingPickup));
        when(pickupRepository.save(any(Pickup.class))).thenReturn(updatedPickup);
        
        // Act
        deliveryPickupUseCase.delivery(orderId);
        
        // Assert
        ArgumentCaptor<Pickup> pickupCaptor = ArgumentCaptor.forClass(Pickup.class);
        verify(pickupRepository).save(pickupCaptor.capture());
        
        Pickup capturedPickup = pickupCaptor.getValue();
        assertEquals(orderId, capturedPickup.getOrderId());
        assertEquals(StatusPickup.FINALIZADO, capturedPickup.getStatusPickup());
        
        ArgumentCaptor<StatusUpdateRequest> statusCaptor = ArgumentCaptor.forClass(StatusUpdateRequest.class);
        verify(orderClient).updateOrderStatus(eq(orderId), statusCaptor.capture());
        assertEquals("FINALIZADO", statusCaptor.getValue().getStatus());
    }
}