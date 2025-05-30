package com.snackbar.cooking.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.cooking.infrastructure.gateways.CookingEntityMapper;
import com.snackbar.cooking.infrastructure.gateways.CookingRepositoryGateway;
import com.snackbar.cooking.infrastructure.gateways.OrderIntegrationGateway;
import com.snackbar.cooking.infrastructure.gateways.OrderResponseMapper;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;
import com.snackbar.cooking.application.usecases.CreateCookingUseCase;
import com.snackbar.cooking.application.usecases.GetAllCookingsUseCase;
import com.snackbar.cooking.application.usecases.GetCookingByOrderIdUseCase;
import com.snackbar.cooking.application.usecases.StartPreparationUseCase;
import com.snackbar.cooking.application.usecases.FinishPreparationUseCase;
import com.snackbar.cooking.application.usecases.ReceiveOrderUseCase;
import com.snackbar.cooking.infrastructure.controllers.CookingDTOMapper;

@Configuration
public class CookingConfig {

    @Bean
    public CookingDTOMapper cookingDTOMapper() {
        return new CookingDTOMapper();
    }

    @Bean
    public CookingEntityMapper cookingEntityMapper() {
        return new CookingEntityMapper();
    }

    @Bean
    public OrderResponseMapper orderResponseMapper() {
        return new OrderResponseMapper();
    }

    @Bean
    public CookingGateway cookingGateway(CookingRepository cookingRepository, CookingEntityMapper cookingMapper) {
        return new CookingRepositoryGateway(cookingRepository, cookingMapper);
    }



    @Bean
    public CreateCookingUseCase createCookingUseCase(CookingGateway cookingGateway, OrderGateway orderGateway) {
        return new CreateCookingUseCase(cookingGateway, orderGateway);
    }

    @Bean
    public StartPreparationUseCase startPreparationUseCase(CookingGateway cookingGateway, CookingRepository cookingRepository, OrderGateway orderGateway) {
        return new StartPreparationUseCase(cookingGateway, orderGateway);
    }

    @Bean
    public FinishPreparationUseCase finishPreparationUseCase(CookingGateway cookingGateway, CookingRepository cookingRepository, OrderGateway orderGateway) {
        return new FinishPreparationUseCase(cookingGateway, orderGateway);
    }

    @Bean
    public ReceiveOrderUseCase receiveOrderUseCase(OrderGateway orderGateway) {
        return new ReceiveOrderUseCase(orderGateway);
    }

    @Bean
    public GetAllCookingsUseCase getAllCookingsUseCase(CookingGateway cookingGateway) {
        return new GetAllCookingsUseCase(cookingGateway);
    }

    @Bean
    public GetCookingByOrderIdUseCase getCookingByOrderIdUseCase(CookingGateway cookingGateway) {
        return new GetCookingByOrderIdUseCase(cookingGateway);
    }
}