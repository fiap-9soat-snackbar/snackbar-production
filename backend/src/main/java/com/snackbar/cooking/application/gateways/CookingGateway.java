package com.snackbar.cooking.application.gateways;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;

import java.util.List;

public interface CookingGateway {
    Cooking createCooking(Cooking cooking);
    Cooking updateCookingStatus(String id, StatusOrder status);
    Cooking findByOrderId(String orderId);
    List<Cooking> findAll();
}