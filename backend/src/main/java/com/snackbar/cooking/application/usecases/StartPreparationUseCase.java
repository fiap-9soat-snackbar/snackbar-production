package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;

import org.springframework.stereotype.Service;

@Service
public class StartPreparationUseCase {

    private final CookingGateway cookingGateway;
    private final CookingRepository cookingRepository;
    private final OrderGateway orderGateway;
    private static final String STATUS_RECEIVED = "RECEBIDO";
    private static final String STATUS_PREPARATION = "PREPARACAO";

    public StartPreparationUseCase(CookingGateway cookingGateway, CookingRepository cookingRepository, OrderGateway orderGateway) {
        this.cookingGateway = cookingGateway;
        this.cookingRepository = cookingRepository;
        this.orderGateway = orderGateway;
    }

    public Cooking updateCooking(Cooking cooking) {
        // 1. Get and validate order
        Order order = orderGateway.findById(cooking.orderId())
            .orElseThrow(() -> new CookingOperationException("Order not found: " + cooking.orderId()));
        validateOrderStatus(order, STATUS_RECEIVED);
        
        try {
            // 2. Update cooking status to PREPARACAO
            cookingGateway.updateCookingStatus(cooking.orderId(), StatusOrder.PREPARACAO);
            Cooking savedCooking = cookingGateway.findByOrderId(cooking.orderId());
            
            // 3. Update order status
            orderGateway.updateStatus(cooking.orderId(), STATUS_PREPARATION);

            return savedCooking;
            
        } catch (Exception e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        }
    }

    private void validateOrderStatus(Order order, String expectedStatus) {
        if (!expectedStatus.equals(order.statusOrder())) {
            throw new OrderStatusInvalidException(
                "Order must be in RECEBIDO status to start preparation. Current status: " + order.statusOrder()
            );
        }
    }
}