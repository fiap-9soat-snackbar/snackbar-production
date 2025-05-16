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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    }
}