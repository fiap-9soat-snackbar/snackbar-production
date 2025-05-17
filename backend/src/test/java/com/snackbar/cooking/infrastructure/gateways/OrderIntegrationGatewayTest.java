package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.exceptions.OrderUpdateException;
import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.orderintegration.dto.OrderResponse;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderIntegrationGatewayTest {

    private OrderClient orderClient;
    private OrderResponseMapper orderMapper;
    private OrderIntegrationGateway gateway;

    @BeforeEach
    void setUp() {
        orderClient = mock(OrderClient.class);
        orderMapper = mock(OrderResponseMapper.class);
        gateway = new OrderIntegrationGateway(orderClient, orderMapper);
    }

    @Test
    void findByIdReturnsOrderWhenFound() {
        String orderId = "order-123";
        OrderResponse response = mock(OrderResponse.class);
        Order order = mock(Order.class);

        when(orderClient.getOrder(orderId)).thenReturn(response);
        when(orderMapper.toEntity(response)).thenReturn(order);

        Optional<Order> result = gateway.findById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());

        verify(orderClient).getOrder(orderId);
        verify(orderMapper).toEntity(response);
    }

    @Test
    void findByIdReturnsEmptyWhenExceptionThrown() {
        String orderId = "order-123";
        when(orderClient.getOrder(orderId)).thenThrow(new RuntimeException("Service down"));

        Optional<Order> result = gateway.findById(orderId);

        assertTrue(result.isEmpty());
    }

    @Test
    void updateStatusReturnsUpdatedOrder() {
        String orderId = "order-123";
        String newStatus = "PREPARACAO";

        OrderResponse response = mock(OrderResponse.class);
        Order updatedOrder = mock(Order.class);

        // Mock update call
        doNothing().when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        // Mock getOrder call after update
        when(orderClient.getOrder(orderId)).thenReturn(response);
        when(orderMapper.toEntity(response)).thenReturn(updatedOrder);

        Order result = gateway.updateStatus(orderId, newStatus);

        assertEquals(updatedOrder, result);

        // Capture the argument passed to updateOrderStatus
        ArgumentCaptor<StatusUpdateRequest> captor = ArgumentCaptor.forClass(StatusUpdateRequest.class);
        verify(orderClient).updateOrderStatus(eq(orderId), captor.capture());

        assertEquals(newStatus, captor.getValue().getStatus());

        verify(orderClient).getOrder(orderId);
        verify(orderMapper).toEntity(response);
    }

    @Test
    void updateStatusThrowsExceptionWhenUpdateFails() {
        String orderId = "order-123";
        String newStatus = "PRONTO";

        doThrow(new RuntimeException("Failed update")).when(orderClient)
                .updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));

        assertThrows(OrderUpdateException.class, () -> gateway.updateStatus(orderId, newStatus));
    }
}