package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.infrastructure.persistence.CookingEntity;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class CookingEntityMapper {

   CookingEntity toEntity(Cooking cookingDomainObj) {
        return new CookingEntity(
            cookingDomainObj.id(),
            cookingDomainObj.orderId(),
            cookingDomainObj.status()
        );
    }

    Cooking toDomainObj(CookingEntity savedEntity) {
        return new Cooking(
            savedEntity.getId(),
            savedEntity.getOrderId(),
            savedEntity.getStatus()
            );
    }

}