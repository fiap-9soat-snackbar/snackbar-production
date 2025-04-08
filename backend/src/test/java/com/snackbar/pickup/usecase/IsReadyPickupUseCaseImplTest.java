package com.snackbar.pickup.usecase;

import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class IsReadyPickupUseCaseImplTest {

    @Mock
    private PickupRepository pickupRepository;

    @InjectMocks
    private IsReadyPickupUseCaseImpl isReadyPickupUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsReady_whenPickupIsReady_shouldReturnTrue() {
        // Arrange
        String orderId = "123";
        Pickup pickup = new Pickup();
        pickup.setStatusPickup(StatusPickup.PRONTO);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(pickup));

        // Act
        boolean isReady = isReadyPickupUseCaseImpl.isReady(orderId);

        // Assert
        assertTrue(isReady);
    }

    @Test
    void testIsReady_whenPickupIsNotFound_shouldReturnFalse() {
        // Arrange
        String orderId = "123";
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act
        boolean isReady = isReadyPickupUseCaseImpl.isReady(orderId);

        // Assert
        assertFalse(isReady);
    }

    @Test
    void testIsDone_whenPickupIsFinalized_shouldReturnTrue() {
        // Arrange
        String orderId = "123";
        Pickup pickup = new Pickup();
        pickup.setStatusPickup(StatusPickup.FINALIZADO);
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.of(pickup));

        // Act
        boolean isDone = isReadyPickupUseCaseImpl.isDone(orderId);

        // Assert
        assertTrue(isDone);
    }

    @Test
    void testIsDone_whenPickupIsNotFound_shouldReturnFalse() {
        // Arrange
        String orderId = "123";
        
        when(pickupRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act
        boolean isDone = isReadyPickupUseCaseImpl.isDone(orderId);

        // Assert
        assertFalse(isDone);
    }
}
