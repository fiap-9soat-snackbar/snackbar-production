package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.exceptions.OrderUpdateException;
import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.orderintegration.dto.OrderResponse;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementação do OrderGateway que usa o OrderClient do orderintegration
 * para fazer chamadas HTTP ao serviço Order externo
 */
@Component
public class OrderIntegrationGateway implements OrderGateway {

    private final OrderClient orderClient;
    private final OrderResponseMapper orderMapper;

    public OrderIntegrationGateway(OrderClient orderClient, OrderResponseMapper orderResponseMapper) {
        this.orderClient = orderClient;
        this.orderMapper = orderResponseMapper;
    }

    @Override
    public Optional<Order> findById(String id) {
        try {
            OrderResponse response = orderClient.getOrder(id);
            return Optional.of(orderMapper.toEntity(response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Order updateStatus(String id, String status) {
        try {
            // Usando o StatusUpdateRequest do orderintegration para atualizar o status
            orderClient.updateOrderStatus(id, new StatusUpdateRequest(status));
            OrderResponse response = orderClient.getOrder(id);
            return orderMapper.toEntity(response);
        } catch (Exception e) {
            throw new OrderUpdateException("Failed to update order status", e);
        }
    }
}
