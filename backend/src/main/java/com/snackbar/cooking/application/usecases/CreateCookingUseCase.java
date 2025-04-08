package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
import com.snackbar.cooking.domain.exceptions.OrderUpdateException;
import com.snackbar.order.domain.model.Order;
import com.snackbar.order.domain.model.StatusOrder;
import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.order.service.OrderService;


import java.math.BigDecimal;

import org.springframework.stereotype.Service;


@Service
public class CreateCookingUseCase {
    private final CookingGateway cookingGateway;
    private final OrderService orderService;

    public CreateCookingUseCase(CookingGateway cookingGateway, OrderService orderService) {
        this.cookingGateway = cookingGateway;
        this.orderService = orderService;
    }

    public Cooking createCooking(Cooking cooking) {
        // 1. Get and validate order
        Order order = orderService.searchOrderId(cooking.orderId());
        validateOrderStatus(order, StatusOrder.PAGO);

        try {
            // 2. Create cooking record with RECEBIDO status
            String id = null;
            Cooking localCooking = new Cooking(id, cooking.orderId(), StatusOrder.RECEBIDO);
            Cooking savedCooking = cookingGateway.createCooking(localCooking);
            
            // 3. Update order status
            orderService.updateOrderStatus(cooking.orderId(), StatusOrder.RECEBIDO);

            return savedCooking;
            
        } catch (Exception e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        }
    }

    private void validateOrderStatus(Order order, StatusOrder currentStatusOrder) {
        if (order.getStatusOrder() != currentStatusOrder) {
            throw new OrderStatusInvalidException(
                "Order must be in PAGO status to be received for cooking. Current status: " + order.getStatusOrder()
            );
        }
    }

    private void updateOrderStatus(Order order) {
        try {
            order.setStatusOrder(StatusOrder.RECEBIDO);
            orderService.updateOrder(order);
        } catch (Exception e) {
            throw new OrderUpdateException("Failed to update order status", e);
        }
    }
}