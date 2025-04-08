package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.application.gateways.CookingGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCookingsUseCase {
    private final CookingGateway cookingGateway;

    public GetAllCookingsUseCase(CookingGateway cookingGateway) {
        this.cookingGateway = cookingGateway;
    }

    public List<Cooking> execute() {
        return cookingGateway.findAll();
    }
}