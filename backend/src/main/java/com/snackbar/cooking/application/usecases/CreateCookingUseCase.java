package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;

import org.springframework.stereotype.Service;

@Service
public class CreateCookingUseCase {
    private final CookingGateway cookingGateway;
    private final OrderGateway orderGateway;
    private static final String STATUS_PAID = "PAGO";
    private static final String STATUS_RECEIVED = "RECEBIDO";

    public CreateCookingUseCase(CookingGateway cookingGateway, OrderGateway orderGateway) {
        this.cookingGateway = cookingGateway;
        this.orderGateway = orderGateway;
    }

    public Cooking createCooking(Cooking cooking) {
        // 1. Get and validate order
        Order order = orderGateway.findById(cooking.orderId())
            .orElseThrow(() -> new CookingOperationException("Order not found: " + cooking.orderId()));
        validateOrderStatus(order, STATUS_PAID);

        try {
            // 2. Create cooking record with RECEBIDO status
            String id = null;
            Cooking localCooking = new Cooking(id, cooking.orderId(), StatusOrder.RECEBIDO);
            Cooking savedCooking = cookingGateway.createCooking(localCooking);
            
            // 3. Update order status
            orderGateway.updateStatus(cooking.orderId(), STATUS_RECEIVED);

            return savedCooking;
            
        } catch (Exception e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        }
    }

    private void validateOrderStatus(Order order, String expectedStatus) {
        if (!expectedStatus.equals(order.statusOrder())) {
            throw new OrderStatusInvalidException(
                "Order must be in PAGO status to be received for cooking. Current status: " + order.statusOrder()
            );
        }
    }
}