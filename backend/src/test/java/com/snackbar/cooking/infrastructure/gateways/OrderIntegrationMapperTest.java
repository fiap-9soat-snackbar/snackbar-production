package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.orderintegration.dto.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderIntegrationMapperTest {

    private OrderIntegrationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderIntegrationMapper();
    }

    @Test
    void toEntityReturnsNullIfResponseIsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toEntityMapsOrderResponseCorrectly() {
        // Arrange
        OrderResponse response = new OrderResponse();
        response.setId("order-123");
        response.setStatusOrder("RECEBIDO");

        // Act
        Order order = mapper.toEntity(response);

        // Assert
        assertNotNull(order);
        assertEquals("order-123", order.id());
        assertNull(order.name());
        assertNull(order.orderNumber());
        assertNotNull(order.orderDateTime());
        assertTrue(order.items().isEmpty());
        assertEquals("RECEBIDO", order.statusOrder());
        assertEquals(0, order.waitingTime());
        assertEquals(0, order.remainingWaitingTime());

        // Optional: Verify that orderDateTime is approximately now (within a few seconds)
        LocalDateTime now = LocalDateTime.now();
        assertTrue(order.orderDateTime().isBefore(now.plusSeconds(5)));
        assertTrue(order.orderDateTime().isAfter(now.minusSeconds(5)));
    }
}