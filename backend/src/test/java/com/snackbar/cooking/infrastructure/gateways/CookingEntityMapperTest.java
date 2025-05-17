package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.CookingStatus;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.infrastructure.persistence.CookingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CookingEntityMapperTest {

    private CookingEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CookingEntityMapper();
    }

    @Test
    void shouldMapDomainToEntity() {
        // given
        Cooking domain = new Cooking("1", "order-123", StatusOrder.PREPARACAO);

        // when
        CookingEntity entity = mapper.toEntity(domain);

        // then
        assertNotNull(entity);
        assertEquals("1", entity.getId());
        assertEquals("order-123", entity.getOrderId());
        assertEquals(StatusOrder.PREPARACAO, entity.getStatus());
    }

    @Test
    void shouldMapEntityToDomain() {
        // given
        CookingEntity entity = new CookingEntity("2", "order-456", StatusOrder.PREPARACAO);

        // when
        Cooking domain = mapper.toDomainObj(entity);

        // then
        assertNotNull(domain);
        assertEquals("2", domain.id());
        assertEquals("order-456", domain.orderId());
        assertEquals(StatusOrder.PREPARACAO, domain.status());
    }
}