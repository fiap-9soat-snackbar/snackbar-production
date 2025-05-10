package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.OrderItem;
import com.snackbar.orderintegration.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderResponseMapper {

    public Order toEntity(OrderResponse response) {
        if (response == null)
            return null;

        // Como o OrderResponse do orderintegration tem menos campos que o Order do
        // cooking,
        // vamos criar um Order com os campos disponíveis e deixar os outros como null
        // ou vazios
        return new Order(
                response.getId(),
                null, // name
                null, // orderNumber
                LocalDateTime.now(), // orderDateTime
                new ArrayList<>(), // items
                response.getStatusOrder(),
                0, // waitingTime
                0 // remainingWaitingTime
        );
    }
}
