package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.CookingGateway;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllCookingsUseCaseTest {

    @Mock
    private CookingGateway cookingGateway;

    @InjectMocks
    private GetAllCookingsUseCase getAllCookingsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldReturnAllCookings() {
        // Arrange
        Cooking cooking1 = new Cooking("cooking1", "order1", StatusOrder.RECEBIDO);
        Cooking cooking2 = new Cooking("cooking2", "order2", StatusOrder.PREPARACAO);
        Cooking cooking3 = new Cooking("cooking3", "order3", StatusOrder.PRONTO);
        
        List<Cooking> expectedCookings = Arrays.asList(cooking1, cooking2, cooking3);
        
        when(cookingGateway.findAll()).thenReturn(expectedCookings);
        
        // Act
        List<Cooking> result = getAllCookingsUseCase.execute();
        
        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(expectedCookings, result);
        
        verify(cookingGateway).findAll();
    }

    @Test
    void execute_whenNoCookingsExist_shouldReturnEmptyList() {
        // Arrange
        when(cookingGateway.findAll()).thenReturn(Collections.emptyList());
        
        // Act
        List<Cooking> result = getAllCookingsUseCase.execute();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(cookingGateway).findAll();
    }
}