package com.snackbar.pickup.usecase;

import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import com.snackbar.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class NotifyCustomerUseCaseImplTest {

    @Mock
    private PickupRepository pickupRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private NotifyCustomerUseCaseImpl notifyCustomerUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNotify_whenOrderExists_shouldNotifyCustomerAndUpdateStatus() {
        // Arrange
        String orderId = "123";
        Pickup pickup = new Pickup();
        pickup.setOrderId(orderId);
        pickup.setStatusPickup(StatusPickup.PRONTO);
        pickup.setNotifyCustomer(true);

        // Act
        notifyCustomerUseCaseImpl.notify(orderId);

        // Assert
        verify(pickupRepository).save(any(Pickup.class));  // Verifica se o repositório salvou o Pickup
        verify(orderService).updateStatusOrder(orderId);  // Verifica se o status do pedido foi atualizado no OrderService
        verify(pickupRepository, times(1)).save(any(Pickup.class));  // Certifica que o save foi chamado uma vez
        verify(orderService, times(1)).updateStatusOrder(orderId);  // Certifica que o updateStatusOrder foi chamado uma vez
    }

    @Test
    void testNotify_whenSavingPickup_shouldSetCorrectValues() {
        // Arrange
        String orderId = "123";

        // Act
        notifyCustomerUseCaseImpl.notify(orderId);

        // Assert
        verify(pickupRepository).save(argThat(pickup -> 
            pickup.getOrderId().equals(orderId) && 
            pickup.getStatusPickup() == StatusPickup.PRONTO &&
            pickup.isNotifyCustomer() == true
        ));  // Verifica se o Pickup foi salvo com os valores corretos
    }
}
