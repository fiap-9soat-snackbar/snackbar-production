package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.OrderItem;
import com.snackbar.cooking.infrastructure.persistence.OrderEntityCooking;
import com.snackbar.cooking.infrastructure.persistence.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEntityMapperCooking {

    public Order toDomain(OrderEntityCooking entity) {
        if (entity == null) return null;

        List<OrderItem> items = entity.getItemEntities().stream()
            .map(this::toOrderItem)
            .collect(Collectors.toList());

        return new Order(
            entity.getId(),
            entity.getName(),
            entity.getOrderNumber(),
            entity.getOrderDateTime(),
            items,
            entity.getStatusOrder(),
            entity.getWaitingTime(),
            entity.getRemainingWaitingTime()
        );
    }

    private OrderItem toOrderItem(OrderItemEntity entity) {
        return new OrderItem(
            entity.getName(),
            entity.getPrice(),
            entity.getQuantity(),
            entity.getCookingTime(),
            entity.getCustomization()
        );
    }

    public OrderEntityCooking toEntity(Order domain) {
        if (domain == null) return null;

        List<OrderItemEntity> itemEntities = domain.items().stream()
            .map(this::toOrderItemEntity)
            .collect(Collectors.toList());

        return new OrderEntityCooking(
            domain.id(),
            domain.name(),
            domain.orderNumber(),
            domain.orderDateTime(),
            itemEntities,
            domain.statusOrder(),
            domain.waitingTime(),
            domain.remainingWaitingTime()
        );
    }

    private OrderItemEntity toOrderItemEntity(OrderItem item) {
        return new OrderItemEntity(
            item.name(),
            item.price(),
            item.quantity(),
            item.cookingTime(),
            item.customization()
        );
    }
}