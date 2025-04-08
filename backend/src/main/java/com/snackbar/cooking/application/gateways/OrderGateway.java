package com.snackbar.cooking.application.gateways;

import com.snackbar.cooking.domain.entity.Order;
import java.util.Optional;

public interface OrderGateway {
    Optional<Order> findById(String id);
    Order updateStatus(String id, String status);
}