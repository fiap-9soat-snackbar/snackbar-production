package com.snackbar.pickup.presentation;

import com.snackbar.pickup.usecase.DeliveryPickupUseCase;
import com.snackbar.pickup.usecase.IsReadyPickupUseCase;
import com.snackbar.pickup.usecase.NotifyCustomerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PickupControllerTest {

    private MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.standaloneSetup(pickupController)
                .setControllerAdvice(new PickupExceptionHandler())
                .build();
    }

    @Test
    void customerNotify_withValidOrderId_shouldReturnSuccess() throws Exception {
        // Arrange
        String orderId = "order123";
        doNothing().when(notifyCustomerUseCase).notify(orderId);

        // Act & Assert
        mockMvc.perform(post("/api/pickup/notify/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente notificado - Pedido: " + orderId));

        verify(notifyCustomerUseCase).notify(orderId);
    }

    @Test
    void customerNotify_withEmptyOrderId_shouldThrowException() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/pickup/notify")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(notifyCustomerUseCase, never()).notify(anyString());
    }

    @Test
    void deliveryOrder_withValidOrderId_shouldReturnSuccess() throws Exception {
        // Arrange
        String orderId = "order123";
        doNothing().when(deliveryPickupUseCase).delivery(orderId);

        // Act & Assert
        mockMvc.perform(post("/api/pickup/delivery/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido " + orderId + " está Finalizado!"));

        verify(deliveryPickupUseCase).delivery(orderId);
    }

    @Test
    void deliveryOrder_withEmptyOrderId_shouldThrowException() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/pickup/delivery")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(deliveryPickupUseCase, never()).delivery(anyString());
    }

    @Test
    void isOrderReady_withValidOrderId_shouldReturnTrue() throws Exception {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isReady(orderId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/pickup/ready/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(isReadyPickupUseCase).isReady(orderId);
    }

    @Test
    void isOrderReady_withValidOrderId_shouldReturnFalse() throws Exception {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isReady(orderId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/api/pickup/ready/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(isReadyPickupUseCase).isReady(orderId);
    }

    @Test
    void isOrderDone_withValidOrderId_shouldReturnTrue() throws Exception {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isDone(orderId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/pickup/done/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(isReadyPickupUseCase).isDone(orderId);
    }

    @Test
    void isOrderDone_withValidOrderId_shouldReturnFalse() throws Exception {
        // Arrange
        String orderId = "order123";
        when(isReadyPickupUseCase.isDone(orderId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/api/pickup/done/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(isReadyPickupUseCase).isDone(orderId);
    }

    @Test
    void customerNotify_whenUseCaseThrowsException_shouldPropagateException() throws Exception {
        // Arrange
        String orderId = "order123";
        doThrow(new IllegalArgumentException("Order not found")).when(notifyCustomerUseCase).notify(orderId);

        // Act & Assert
        mockMvc.perform(post("/api/pickup/notify/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Order not found"));

        verify(notifyCustomerUseCase).notify(orderId);
    }

    @Test
    void deliveryOrder_whenUseCaseThrowsException_shouldPropagateException() throws Exception {
        // Arrange
        String orderId = "order123";
        doThrow(new IllegalArgumentException("Order not found")).when(deliveryPickupUseCase).delivery(orderId);

        // Act & Assert
        mockMvc.perform(post("/api/pickup/delivery/{orderId}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Order not found"));

        verify(deliveryPickupUseCase).delivery(orderId);
    }
}