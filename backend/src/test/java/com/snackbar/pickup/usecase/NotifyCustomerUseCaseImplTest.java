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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}