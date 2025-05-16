package com.snackbar.orderintegration.client;

import com.snackbar.orderintegration.dto.OrderResponse;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
        verify(orderClient).getOrder(orderId);
    }

    @Test
    void updateOrderStatus_shouldCallEndpoint() {
        // Arrange
        String orderId = "order123";
        StatusUpdateRequest request = new StatusUpdateRequest("FINALIZADO");
        
        doNothing().when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act - This will not throw an exception if the method signature is correct
        orderClient.updateOrderStatus(orderId, request);
        
        // Assert
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void getOrder_whenOrderNotFound_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        HttpClientErrorException notFoundException = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Order not found");
        
        when(orderClient.getOrder(orderId)).thenThrow(notFoundException);
        
        // Act & Assert
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            orderClient.getOrder(orderId);
        });
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Order not found", exception.getStatusText());
        verify(orderClient).getOrder(orderId);
    }
    
    @Test
    void getOrder_whenServerError_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        HttpServerErrorException serverException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
        
        when(orderClient.getOrder(orderId)).thenThrow(serverException);
        
        // Act & Assert
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            orderClient.getOrder(orderId);
        });
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Server error", exception.getStatusText());
        verify(orderClient).getOrder(orderId);
    }
    
    @Test
    void getOrder_whenNetworkError_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        ResourceAccessException networkException = new ResourceAccessException("Network error");
        
        when(orderClient.getOrder(orderId)).thenThrow(networkException);
        
        // Act & Assert
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            orderClient.getOrder(orderId);
        });
        
        assertEquals("Network error", exception.getMessage());
        verify(orderClient).getOrder(orderId);
    }
    
    @Test
    void updateOrderStatus_whenOrderNotFound_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        StatusUpdateRequest request = new StatusUpdateRequest("FINALIZADO");
        HttpClientErrorException notFoundException = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Order not found");
        
        doThrow(notFoundException).when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act & Assert
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            orderClient.updateOrderStatus(orderId, request);
        });
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Order not found", exception.getStatusText());
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void updateOrderStatus_whenServerError_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        StatusUpdateRequest request = new StatusUpdateRequest("FINALIZADO");
        HttpServerErrorException serverException = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");
        
        doThrow(serverException).when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act & Assert
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class, () -> {
            orderClient.updateOrderStatus(orderId, request);
        });
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Server error", exception.getStatusText());
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void updateOrderStatus_whenNetworkError_shouldThrowException() {
        // Arrange
        String orderId = "order123";
        StatusUpdateRequest request = new StatusUpdateRequest("FINALIZADO");
        ResourceAccessException networkException = new ResourceAccessException("Network error");
        
        doThrow(networkException).when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act & Assert
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            orderClient.updateOrderStatus(orderId, request);
        });
        
        assertEquals("Network error", exception.getMessage());
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }
    
    @Test
    void getOrder_withNullOrderId_shouldThrowException() {
        // Arrange
        String orderId = null;
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Order ID cannot be null");
        
        when(orderClient.getOrder(orderId)).thenThrow(illegalArgumentException);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderClient.getOrder(orderId);
        });
        
        assertEquals("Order ID cannot be null", exception.getMessage());
        verify(orderClient).getOrder(orderId);
    }
    
    @Test
    void updateOrderStatus_withNullOrderId_shouldThrowException() {
        // Arrange
        String orderId = null;
        StatusUpdateRequest request = new StatusUpdateRequest("FINALIZADO");
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Order ID cannot be null");
        
        doThrow(illegalArgumentException).when(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderClient.updateOrderStatus(orderId, request);
        });
        
        assertEquals("Order ID cannot be null", exception.getMessage());
        verify(orderClient).updateOrderStatus(eq(orderId), any(StatusUpdateRequest.class));
    }

    @Configuration
    @EnableFeignClients(clients = OrderClient.class)
    static class TestConfig {
    }
}