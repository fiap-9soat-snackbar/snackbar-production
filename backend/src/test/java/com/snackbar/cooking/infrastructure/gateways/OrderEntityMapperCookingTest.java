package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.OrderItem;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.infrastructure.persistence.OrderEntityCooking;
import com.snackbar.cooking.infrastructure.persistence.OrderItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityMapperCookingTest {

    private OrderEntityMapperCooking mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderEntityMapperCooking();
    }

    @Test
    void shouldMapDomainToEntity() {
        OrderItem item = new OrderItem("Burger", 10.99, 2, 15, "No onions");
        Order domain = new Order(
            "order-001",
            "John Doe",
            "123",
            LocalDateTime.of(2023, 5, 1, 12, 30),
            List.of(item),
            StatusOrder.RECEBIDO.name(),
            20,
            10
        );

        OrderEntityCooking entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals("order-001", entity.getId());
        assertEquals("John Doe", entity.getName());
        assertEquals("123", entity.getOrderNumber());
        assertEquals(StatusOrder.RECEBIDO.name(), entity.getStatusOrder());
        assertEquals(1, entity.getItemEntities().size());

        OrderItemEntity mappedItem = entity.getItemEntities().get(0);
        assertEquals("Burger", mappedItem.getName());
        assertEquals(10.99, mappedItem.getPrice());
        assertEquals(2, mappedItem.getQuantity());
        assertEquals(15, mappedItem.getCookingTime());
        assertEquals("No onions", mappedItem.getCustomization());
    }

    @Test
    void shouldMapEntityToDomain() {
        OrderItemEntity itemEntity = new OrderItemEntity("Pizza", 12.50, 1, 20, "Extra cheese");

        OrderEntityCooking entity = new OrderEntityCooking(
            "order-002",
            "Alice Smith",
            "456",
            LocalDateTime.of(2023, 5, 2, 18, 45),
            List.of(itemEntity),
            StatusOrder.PREPARACAO.name(),
            25,
            5
        );

        Order domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("order-002", domain.id());
        assertEquals("Alice Smith", domain.name());
        assertEquals("456", domain.orderNumber());
        assertEquals(StatusOrder.PREPARACAO.name(), domain.statusOrder());
        assertEquals(1, domain.items().size());

        OrderItem mappedItem = domain.items().get(0);
        assertEquals("Pizza", mappedItem.name());
        assertEquals(12.50, mappedItem.price());
        assertEquals(1, mappedItem.quantity());
        assertEquals(20, mappedItem.cookingTime());
        assertEquals("Extra cheese", mappedItem.customization());
    }

    @Test
    void shouldReturnNullWhenMappingNullEntity() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void shouldReturnNullWhenMappingNullDomain() {
        assertNull(mapper.toEntity(null));
    }
}