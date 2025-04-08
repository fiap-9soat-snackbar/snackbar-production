package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.domain.exceptions.CookingNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetCookingByOrderIdUseCase {
    private final CookingGateway cookingGateway;

    public GetCookingByOrderIdUseCase(CookingGateway cookingGateway) {
        this.cookingGateway = cookingGateway;
    }

    public Cooking execute(String orderId) {
        Cooking cooking = cookingGateway.findByOrderId(orderId);
        if (cooking == null) {
            throw new CookingNotFoundException("Cooking not found for order ID: " + orderId);
        }
        return cooking;
    }
}