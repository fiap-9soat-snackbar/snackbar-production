package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.order.domain.model.StatusOrder;

public class CookingDTOMapper {
    
    public Cooking createRequestToDomain(String orderId) {

        String myOrderId = orderId;
        String id = null;
        StatusOrder status = null;
        Cooking localCooking = new Cooking(id, myOrderId, status);
        return localCooking;
    }

    CreateCookingResponse createToResponse(Cooking cooking) {
        return new CreateCookingResponse(cooking.id(), cooking.orderId(), cooking.status());
    }
}
