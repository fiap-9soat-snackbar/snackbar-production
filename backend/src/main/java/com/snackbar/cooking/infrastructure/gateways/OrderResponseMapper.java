package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.client.OrderItemResponse;
import com.snackbar.cooking.client.OrderResponse;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderResponseMapper {

    public Order toEntity(OrderResponse response) {
        List<OrderItem> items = response.items().stream()
                .map(this::toOrderItem)
                .collect(Collectors.toList());

        return new Order(
                response.id(),
                response.name(),
                response.orderNumber(),
                response.orderDateTime(),
                items,
                response.statusOrder(),
                response.waitingTime(),
                response.remainingWaitingTime()
        );
    }

    private OrderItem toOrderItem(OrderItemResponse itemResponse) {
        return new OrderItem(
                itemResponse.name(),
                itemResponse.price(),
                itemResponse.quantity(),
                itemResponse.cookingTime(),
                itemResponse.customization()
        );
    }
}