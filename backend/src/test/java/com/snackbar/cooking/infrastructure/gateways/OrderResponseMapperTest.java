package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.orderintegration.dto.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseMapperTest {

    private OrderResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderResponseMapper();
    }

    @Test
    void toEntity_shouldReturnNull_whenResponseIsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toEntity_shouldMapResponseToOrder_withDefaults() {
        // Arrange
        OrderResponse response = new OrderResponse();
        response.setId("order-1001");
        response.setStatusOrder("PREPARACAO");

        // Act
        Order order = mapper.toEntity(response);

        // Assert
        assertNotNull(order);
        assertEquals("order-1001", order.id());
        assertNull(order.name());
        assertNull(order.orderNumber());
        assertNotNull(order.orderDateTime());
        assertTrue(order.items().isEmpty());
        assertEquals("PREPARACAO", order.statusOrder());
        assertEquals(0, order.waitingTime());
        assertEquals(0, order.remainingWaitingTime());

        // Validate that orderDateTime is approximately now
        LocalDateTime now = LocalDateTime.now();
        assertTrue(order.orderDateTime().isBefore(now.plusSeconds(5)));
        assertTrue(order.orderDateTime().isAfter(now.minusSeconds(5)));
    }
}