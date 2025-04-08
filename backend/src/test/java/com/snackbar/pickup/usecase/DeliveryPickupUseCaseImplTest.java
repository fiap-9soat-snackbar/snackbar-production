package com.snackbar.pickup.usecase;

import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import com.snackbar.orderRefactory.application.usecases.OrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeliveryPickupUseCaseImplTest {

    @Mock
    private PickupRepository pickupRepository;

    @Mock
    private OrderUseCase orderUseCase;

    @InjectMocks
    private DeliveryPickupUseCaseImpl deliveryPickupUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelivery_whenPickupExists_shouldUpdateStatusToFinalized() {
        // Arrange
        String orderId = "123";
        Pickup pickup = new Pickup();
        pickup.setStatusPickup(StatusPickup.PRONTO);  // Initial status
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(pickup));

        // Act
        deliveryPickupUseCaseImpl.delivery(orderId);

        // Assert
        verify(pickupRepository).save(pickup);  // Ensure that the pickup status was saved
        verify(orderUseCase).updateStatusOrder(orderId, "FINALIZADO");  // Ensure order status was updated in OrderUseCase
        verify(pickupRepository, times(1)).findByOrderId(orderId);  // Ensure repository was queried exactly once
        verify(pickupRepository, times(1)).save(pickup);  // Ensure repository saved the updated pickup
        verify(orderUseCase, times(1)).updateStatusOrder(orderId, "FINALIZADO");  // Ensure status update was called
    }

    @Test
    void testDelivery_whenPickupDoesNotExist_shouldThrowException() {
        // Arrange
        String orderId = "123";
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deliveryPickupUseCaseImpl.delivery(orderId), 
                     "Este pedido não foi retirado: " + orderId);
        
        verify(pickupRepository, times(1)).findByOrderId(orderId);  // Ensure repository was queried exactly once
        verify(pickupRepository, never()).save(any(Pickup.class));  // Ensure repository save was never called
        verify(orderUseCase, never()).updateStatusOrder(anyString(), anyString());  // Ensure update status was never called
    }
}
