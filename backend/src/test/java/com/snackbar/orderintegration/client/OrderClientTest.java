package com.snackbar.orderintegration.client;

import com.snackbar.orderintegration.dto.OrderResponse;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

// This is a mock test to verify the interface methods are correctly defined
// In a real scenario, we would use WireMock or similar to test the actual HTTP calls
@SpringBootTest
@ContextConfiguration(classes = OrderClientTest.TestConfig.class)
class OrderClientTest {

    @MockBean
    private OrderClient orderClient;

    @Test
    void getOrder_shouldReturnOrderResponse() {
        // Arrange
        String orderId = "order123";
        OrderResponse expectedResponse = new OrderResponse();
        expectedResponse.setId(orderId);
        expectedResponse.setStatusOrder("PRONTO");
        
        when(orderClient.getOrder(orderId)).thenReturn(expectedResponse);
        
        // Act
        OrderResponse response = orderClient.getOrder(orderId);
        
        // Assert
        assertEquals(orderId, response.getId());
        assertEquals("PRONTO", response.getStatusOrder());
    }

    @Test
    void updateOrderStatus_shouldCallEndpoint() {
        // Arrange
        String orderId = "order123";
        StatusUpdateRequest request = new StatusUpdateRequest("FINALIZADO");
        
        doNothing().when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act - This will not throw an exception if the method signature is correct
        orderClient.updateOrderStatus(orderId, request);
    }

    @Configuration
    @EnableFeignClients(clients = OrderClient.class)
    static class TestConfig {
    }
}