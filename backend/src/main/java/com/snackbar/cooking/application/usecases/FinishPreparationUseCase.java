package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.Order;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import com.snackbar.cooking.domain.exceptions.OrderStatusInvalidException;
import org.springframework.stereotype.Service;

@Service
public class FinishPreparationUseCase {

    private final CookingGateway cookingGateway;
    private final OrderGateway orderGateway;

    private static final String STATUS_PREPARATION = "PREPARACAO";
    private static final String STATUS_READY = "PRONTO";

    public FinishPreparationUseCase(CookingGateway cookingGateway, OrderGateway orderGateway) {
        this.cookingGateway = cookingGateway;
        this.orderGateway = orderGateway;
    }

    public Cooking updateCooking(Cooking cooking) {
        try {
            // 1. Get and validate order
            Order order = orderGateway.findById(cooking.orderId())
                .orElseThrow(() -> new CookingOperationException("Order not found: " + cooking.orderId()));

            validateOrderStatus(order, STATUS_PREPARATION);

            // 2. Update cooking status to PRONTO
            cookingGateway.updateCookingStatus(cooking.orderId(), StatusOrder.PRONTO);

            // 3. Retrieve the updated cooking record
            Cooking updatedCooking = cookingGateway.findByOrderId(cooking.orderId());

            // 4. Update order status to PRONTO
            orderGateway.updateStatus(cooking.orderId(), STATUS_READY);

            return updatedCooking;

        } catch (CookingOperationException e) {
            // Re-throw CookingOperationException without wrapping to preserve the original message
            throw e;
        } catch (OrderStatusInvalidException e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        } catch (Exception e) {
            throw new CookingOperationException("Failed to finish cooking preparation", e);
        }
    }

    private void validateOrderStatus(Order order, String expectedStatus) {
        if (!expectedStatus.equals(order.statusOrder())) {
            throw new OrderStatusInvalidException(
                "Order must be in " + expectedStatus + " status to finish preparation. Current status: " + order.statusOrder()
            );
        }
    }
}
