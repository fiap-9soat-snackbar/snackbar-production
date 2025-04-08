package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.infrastructure.persistence.OrderRepositoryCooking;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class OrderDatabaseGatewayCooking implements OrderGateway {
    private final OrderRepositoryCooking orderRepository;
    private final OrderEntityMapperCooking mapper;

    public OrderDatabaseGatewayCooking(OrderRepositoryCooking orderRepository, OrderEntityMapperCooking mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Order updateStatus(String id, String status) {
        var entity = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

// Only update the status field, preserve all other fields
        entity.setStatusOrder(status);

        var updatedEntity = orderRepository.save(entity);
        return mapper.toDomain(updatedEntity);
    }
}