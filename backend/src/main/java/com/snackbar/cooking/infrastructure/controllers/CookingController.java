package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.application.usecases.*;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.infrastructure.persistence.CookingEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cooking")
public class CookingController {
    private final CreateCookingUseCase createCookingUseCase;
    private final StartPreparationUseCase startPreparationUseCase;
    private final FinishPreparationUseCase finishPreparationUseCase;
    private final GetAllCookingsUseCase getAllCookingsUseCase;
    private final GetCookingByOrderIdUseCase getCookingByOrderIdUseCase;
    private final CookingDTOMapper cookingDTOMapper;

    public CookingController(CreateCookingUseCase createCookingUseCase, StartPreparationUseCase startPreparationUseCase, FinishPreparationUseCase finishPreparationUseCase, GetAllCookingsUseCase getAllCookingsUseCase, GetCookingByOrderIdUseCase getCookingByOrderIdUseCase, CookingDTOMapper cookingDTOMapper) {
        this.createCookingUseCase = createCookingUseCase;
        this.startPreparationUseCase = startPreparationUseCase;
        this.finishPreparationUseCase = finishPreparationUseCase;
        this.getAllCookingsUseCase = getAllCookingsUseCase;
        this.getCookingByOrderIdUseCase = getCookingByOrderIdUseCase;
        this.cookingDTOMapper = cookingDTOMapper;
    }

    @PostMapping("/receive-order/{id}")
    public ResponseEntity<CreateCookingResponse> receiveOrder(@PathVariable String id) {
        Cooking cooking = cookingDTOMapper.createRequestToDomain(id);
        Cooking createdCooking = createCookingUseCase.createCooking(cooking);
        CreateCookingResponse response = cookingDTOMapper.createToResponse(createdCooking);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/start-preparation/{id}")
    public ResponseEntity<CreateCookingResponse> startPreparation(@PathVariable String id) {
        Cooking cooking = cookingDTOMapper.createRequestToDomain(id);
        Cooking result = startPreparationUseCase.updateCooking(cooking);
        CreateCookingResponse response = cookingDTOMapper.createToResponse(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/finish-preparation/{id}")
    public ResponseEntity<CreateCookingResponse> finishPreparation(@PathVariable String id) {
        Cooking cooking = cookingDTOMapper.createRequestToDomain(id);
        Cooking result = finishPreparationUseCase.updateCooking(cooking);
        CreateCookingResponse response = cookingDTOMapper.createToResponse(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CreateCookingResponse>> getAllCookings() {
        List<Cooking> cookings = getAllCookingsUseCase.execute();
        
        if (cookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CreateCookingResponse> response = cookings.stream()
                .map(cookingDTOMapper::createToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateCookingResponse> getCookingByOrderId(@PathVariable String id) {
        Cooking cooking = getCookingByOrderIdUseCase.execute(id);
        CreateCookingResponse response = cookingDTOMapper.createToResponse(cooking);
        return ResponseEntity.ok(response);
    }
}