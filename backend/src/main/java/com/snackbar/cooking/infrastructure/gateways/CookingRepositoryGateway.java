package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.infrastructure.persistence.CookingEntity;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;
import com.snackbar.order.domain.model.StatusOrder;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CookingRepositoryGateway implements CookingGateway {
    private final CookingRepository cookingRepository;
    private final CookingEntityMapper cookingMapper;

    public CookingRepositoryGateway(CookingRepository cookingRepository, CookingEntityMapper cookingMapper) {
        this.cookingRepository = cookingRepository;
        this.cookingMapper = cookingMapper;
    }

    @Override
    public Cooking createCooking(Cooking cooking) {
        var entity = cookingMapper.toEntity(cooking);
        var savedEntity = cookingRepository.save(entity);
        return cookingMapper.toDomainObj(savedEntity);
    }

    @Override
    public Cooking updateCookingStatus(String orderId, StatusOrder status) {

        CookingEntity entity = cookingRepository.findByOrderId(orderId);
        
        if (entity == null) {
            throw new RuntimeException("Cooking not found for orderId: " + orderId);
        }
        
        entity.setStatus(status);
        var updatedEntity = cookingRepository.save(entity);
        return cookingMapper.toDomainObj(updatedEntity);
    }

    @Override
    public Cooking findByOrderId(String orderId) {
        CookingEntity entity = cookingRepository.findByOrderId(orderId);
        return cookingMapper.toDomainObj(entity);
    }

    @Override
    public List<Cooking> findAll() {
        List<CookingEntity> entities = cookingRepository.findAll();
        return entities.stream()
                .map(cookingMapper::toDomainObj)
                .collect(Collectors.toList());
    }
}