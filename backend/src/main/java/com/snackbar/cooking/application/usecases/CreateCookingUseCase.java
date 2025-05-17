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
        try {
            // 1. Fetch the order
            Order order = orderGateway.findById(cooking.orderId())
                .orElseThrow(() -> new CookingOperationException("Order not found: " + cooking.orderId()));

            // 2. Validate order is in 'PAGO' status
            validateOrderStatus(order, STATUS_PAID);

            // 3. Create cooking with 'RECEBIDO' status
            Cooking cookingToSave = new Cooking(null, cooking.orderId(), StatusOrder.RECEBIDO);
            Cooking savedCooking = cookingGateway.createCooking(cookingToSave);

            // 4. Update order status to 'RECEBIDO'
            orderGateway.updateStatus(cooking.orderId(), STATUS_RECEIVED);

            return savedCooking;
        } catch (CookingOperationException e) {
            // Re-throw CookingOperationException without wrapping to preserve the original message
            throw e;
        } catch (OrderStatusInvalidException e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        } catch (Exception e) {
            throw new CookingOperationException("Failed to process cooking order", e);
        }
    }

    private void validateOrderStatus(Order order, String expectedStatus) {
        if (!expectedStatus.equals(order.statusOrder())) {
            throw new OrderStatusInvalidException(
                "Order must be in " + expectedStatus + " status to be received for cooking. Current status: " + order.statusOrder()
            );
        }
    }
}
