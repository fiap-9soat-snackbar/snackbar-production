package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.OrderItem;
import com.snackbar.orderintegration.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class OrderIntegrationMapper {

    public Order toEntity(OrderResponse response) {
        if (response == null) return null;

        return new Order(
                response.getId(),
                null, 
                null,
                LocalDateTime.now(), 
                new ArrayList<>(), 
                response.getStatusOrder(),
                0, 
                0  
        );
    }
}