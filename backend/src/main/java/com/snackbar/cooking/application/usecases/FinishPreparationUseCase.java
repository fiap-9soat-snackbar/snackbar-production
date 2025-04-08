package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
import com.snackbar.cooking.domain.exceptions.OrderUpdateException;
import com.snackbar.order.domain.model.Order;
import com.snackbar.order.domain.model.StatusOrder;
import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.order.service.OrderService;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;

import org.springframework.stereotype.Service;

@Service
public class FinishPreparationUseCase {
    private final CookingGateway cookingGateway;
    private final CookingRepository cookingRepository;
    private final OrderService orderService;

    public FinishPreparationUseCase(CookingGateway cookingGateway, CookingRepository cookingRepository, OrderService orderService) {
        this.cookingGateway = cookingGateway;
        this.cookingRepository = cookingRepository;
        this.orderService = orderService;
    }

    public Cooking updateCooking(Cooking cooking) {
        // 1. Get and validate order
        Order order = orderService.searchOrderId(cooking.orderId());
        validateOrderStatus(order, StatusOrder.PREPARACAO);
        
        try {
            // 2. Create cooking record with RECEBIDO status
            cookingGateway.updateCookingStatus(cooking.orderId(), StatusOrder.PRONTO);
            Cooking savedCooking = cookingGateway.findByOrderId(cooking.orderId());
            // 3. Update order status
            orderService.updateOrderStatus(cooking.orderId(), StatusOrder.PRONTO);

            return savedCooking;
            
        } catch (Exception e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        }
    }

    private void validateOrderStatus(Order order, StatusOrder currentStatusOrder) {
        if (order.getStatusOrder() != currentStatusOrder) {
            throw new OrderStatusInvalidException(
                "Order must be in PREPARACAO status to be received for cooking. Current status: " + order.getStatusOrder()
            );
        }
    }

}
