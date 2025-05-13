package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.application.usecases.*;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingNotFoundException;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CookingControllerTest {

    @Mock
    private CreateCookingUseCase createCookingUseCase;

    @Mock
    private StartPreparationUseCase startPreparationUseCase;

    @Mock
    private FinishPreparationUseCase finishPreparationUseCase;

    @Mock
    private GetAllCookingsUseCase getAllCookingsUseCase;

    @Mock
    private GetCookingByOrderIdUseCase getCookingByOrderIdUseCase;

    @Mock
    private CookingDTOMapper cookingDTOMapper;

    @InjectMocks
    private CookingController cookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void receiveOrder_shouldReturnCreatedCooking() {
        // Arrange
        String orderId = "order123";
        Cooking domainCooking = new Cooking(null, orderId, null);
        Cooking createdCooking = new Cooking("cooking123", orderId, StatusOrder.RECEBIDO);
        CreateCookingResponse expectedResponse = new CreateCookingResponse("cooking123", orderId, "RECEBIDO");
        
        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(domainCooking);
        when(createCookingUseCase.createCooking(domainCooking)).thenReturn(createdCooking);
        when(cookingDTOMapper.createToResponse(createdCooking)).thenReturn(expectedResponse);
        
        // Act
        ResponseEntity<CreateCookingResponse> response = cookingController.receiveOrder(orderId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        
        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(createCookingUseCase).createCooking(domainCooking);
        verify(cookingDTOMapper).createToResponse(createdCooking);
    }

    @Test
    void startPreparation_shouldReturnUpdatedCooking() {
        // Arrange
        String orderId = "order123";
        Cooking domainCooking = new Cooking(null, orderId, null);
        Cooking updatedCooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        CreateCookingResponse expectedResponse = new CreateCookingResponse("cooking123", orderId, "PREPARACAO");
        
        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(domainCooking);
        when(startPreparationUseCase.updateCooking(domainCooking)).thenReturn(updatedCooking);
        when(cookingDTOMapper.createToResponse(updatedCooking)).thenReturn(expectedResponse);
        
        // Act
        ResponseEntity<CreateCookingResponse> response = cookingController.startPreparation(orderId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        
        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(startPreparationUseCase).updateCooking(domainCooking);
        verify(cookingDTOMapper).createToResponse(updatedCooking);
    }

    @Test
    void finishPreparation_shouldReturnUpdatedCooking() {
        // Arrange
        String orderId = "order123";
        Cooking domainCooking = new Cooking(null, orderId, null);
        Cooking updatedCooking = new Cooking("cooking123", orderId, StatusOrder.PRONTO);
        CreateCookingResponse expectedResponse = new CreateCookingResponse("cooking123", orderId, "PRONTO");
        
        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(domainCooking);
        when(finishPreparationUseCase.updateCooking(domainCooking)).thenReturn(updatedCooking);
        when(cookingDTOMapper.createToResponse(updatedCooking)).thenReturn(expectedResponse);
        
        // Act
        ResponseEntity<CreateCookingResponse> response = cookingController.finishPreparation(orderId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        
        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(finishPreparationUseCase).updateCooking(domainCooking);
        verify(cookingDTOMapper).createToResponse(updatedCooking);
    }

    @Test
    void getAllCookings_whenCookingsExist_shouldReturnListOfCookings() {
        // Arrange
        Cooking cooking1 = new Cooking("cooking1", "order1", StatusOrder.RECEBIDO);
        Cooking cooking2 = new Cooking("cooking2", "order2", StatusOrder.PREPARACAO);
        List<Cooking> cookings = Arrays.asList(cooking1, cooking2);
        
        CreateCookingResponse response1 = new CreateCookingResponse("cooking1", "order1", "RECEBIDO");
        CreateCookingResponse response2 = new CreateCookingResponse("cooking2", "order2", "PREPARACAO");
        List<CreateCookingResponse> expectedResponses = Arrays.asList(response1, response2);
        
        when(getAllCookingsUseCase.execute()).thenReturn(cookings);
        when(cookingDTOMapper.createToResponse(cooking1)).thenReturn(response1);
        when(cookingDTOMapper.createToResponse(cooking2)).thenReturn(response2);
        
        // Act
        ResponseEntity<List<CreateCookingResponse>> response = cookingController.getAllCookings();
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponses, response.getBody());
        
        verify(getAllCookingsUseCase).execute();
        verify(cookingDTOMapper).createToResponse(cooking1);
        verify(cookingDTOMapper).createToResponse(cooking2);
    }

    @Test
    void getAllCookings_whenNoCookingsExist_shouldReturnNoContent() {
        // Arrange
        when(getAllCookingsUseCase.execute()).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<List<CreateCookingResponse>> response = cookingController.getAllCookings();
        
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        
        verify(getAllCookingsUseCase).execute();
        verify(cookingDTOMapper, never()).createToResponse(any(Cooking.class));
    }

    @Test
    void getCookingByOrderId_whenCookingExists_shouldReturnCooking() {
        // Arrange
        String orderId = "order123";
        Cooking cooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        CreateCookingResponse expectedResponse = new CreateCookingResponse("cooking123", orderId, "PREPARACAO");
        
        when(getCookingByOrderIdUseCase.execute(orderId)).thenReturn(cooking);
        when(cookingDTOMapper.createToResponse(cooking)).thenReturn(expectedResponse);
        
        // Act
        ResponseEntity<CreateCookingResponse> response = cookingController.getCookingByOrderId(orderId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        
        verify(getCookingByOrderIdUseCase).execute(orderId);
        verify(cookingDTOMapper).createToResponse(cooking);
    }
}