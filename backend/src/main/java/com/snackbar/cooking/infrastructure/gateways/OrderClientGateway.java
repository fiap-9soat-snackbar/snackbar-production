package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.client.OrderClient;
import com.snackbar.cooking.client.OrderResponse;
import com.snackbar.cooking.domain.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderClientGateway implements OrderGateway {

    private final OrderClient orderClient;
    private final OrderResponseMapper orderResponseMapper;

    public OrderClientGateway(OrderClient orderClient, OrderResponseMapper orderResponseMapper) {
        this.orderClient = orderClient;
        this.orderResponseMapper = orderResponseMapper;
    }

    @Override
    public Optional<Order> findById(String id) {
        try {
            OrderResponse response = orderClient.getOrder(id);
            return Optional.of(orderResponseMapper.toEntity(response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Order updateStatus(String id, String status) {
        orderClient.updateOrderStatus(id, status);
        OrderResponse response = orderClient.getOrder(id);
        return orderResponseMapper.toEntity(response);
    }
}