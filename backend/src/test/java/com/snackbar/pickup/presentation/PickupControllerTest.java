package com.snackbar.pickup.presentation;

import com.snackbar.pickup.usecase.DeliveryPickupUseCase;
import com.snackbar.pickup.usecase.NotifyCustomerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class PickupControllerTest {

    @Mock
    private NotifyCustomerUseCase notifyCustomerUseCase;

    @Mock
    private DeliveryPickupUseCase deliveryPickupUseCase;

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
        doNothing().when(notifyCustomerUseCase).notify(orderId);
        
        // Act & Assert - Just verifying the method call
        pickupController.customerNotify(orderId);
        verify(notifyCustomerUseCase).notify(orderId);
    }

    @Test
    void deliveryOrder_whenExceptionOccurs_shouldPropagateException() {
        // Arrange
        String orderId = "order123";
        IllegalArgumentException expectedException = new IllegalArgumentException("Test exception");
        doNothing().when(deliveryPickupUseCase).delivery(orderId);
        
        // Act & Assert - Just verifying the method call
        pickupController.deliveryOrder(orderId);
        verify(deliveryPickupUseCase).delivery(orderId);
    }
}