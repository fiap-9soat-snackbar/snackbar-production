package com.snackbar.cooking.infrastructure.config;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.application.gateways.OrderGateway;
import com.snackbar.cooking.application.usecases.*;
import com.snackbar.cooking.infrastructure.controllers.CookingDTOMapper;
import com.snackbar.cooking.infrastructure.gateways.*;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CookingConfigTest {

    private CookingConfig config;

    @BeforeEach
    void setUp() {
        config = new CookingConfig();
    }

    @Test
    void shouldCreateCookingDTOMapper() {
        CookingDTOMapper mapper = config.cookingDTOMapper();
        assertNotNull(mapper);
    }

    @Test
    void shouldCreateCookingEntityMapper() {
        CookingEntityMapper mapper = config.cookingEntityMapper();
        assertNotNull(mapper);
    }

    @Test
    void shouldCreateOrderResponseMapper() {
        OrderResponseMapper mapper = config.orderResponseMapper();
        assertNotNull(mapper);
    }

    @Test
    void shouldCreateCookingGateway() {
        CookingRepository repo = mock(CookingRepository.class);
        CookingEntityMapper mapper = mock(CookingEntityMapper.class);
        CookingGateway gateway = config.cookingGateway(repo, mapper);
        assertNotNull(gateway);
        assertTrue(gateway instanceof CookingRepositoryGateway);
    }

    @Test
    void shouldCreateCreateCookingUseCase() {
        CookingGateway cookingGateway = mock(CookingGateway.class);
        OrderGateway orderGateway = mock(OrderGateway.class);
        CreateCookingUseCase useCase = config.createCookingUseCase(cookingGateway, orderGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateStartPreparationUseCase() {
        CookingGateway cookingGateway = mock(CookingGateway.class);
        OrderGateway orderGateway = mock(OrderGateway.class);
        CookingRepository repo = mock(CookingRepository.class);
        StartPreparationUseCase useCase = config.startPreparationUseCase(cookingGateway, repo, orderGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateFinishPreparationUseCase() {
        CookingGateway cookingGateway = mock(CookingGateway.class);
        OrderGateway orderGateway = mock(OrderGateway.class);
        CookingRepository repo = mock(CookingRepository.class);
        FinishPreparationUseCase useCase = config.finishPreparationUseCase(cookingGateway, repo, orderGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateReceiveOrderUseCase() {
        OrderGateway orderGateway = mock(OrderGateway.class);
        ReceiveOrderUseCase useCase = config.receiveOrderUseCase(orderGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateGetAllCookingsUseCase() {
        CookingGateway cookingGateway = mock(CookingGateway.class);
        GetAllCookingsUseCase useCase = config.getAllCookingsUseCase(cookingGateway);
        assertNotNull(useCase);
    }

    @Test
    void shouldCreateGetCookingByOrderIdUseCase() {
        CookingGateway cookingGateway = mock(CookingGateway.class);
        GetCookingByOrderIdUseCase useCase = config.getCookingByOrderIdUseCase(cookingGateway);
        assertNotNull(useCase);
    }
}