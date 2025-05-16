package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.application.usecases.*;
import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.domain.exceptions.CookingOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CookingControllerTest {

    private MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.standaloneSetup(cookingController)
                .setControllerAdvice(new CookingExceptionHandler())
                .build();
    }

    @Test
    void receiveOrder_withValidId_shouldReturnCreatedCooking() throws Exception {
        // Arrange
        String orderId = "order123";
        Cooking cooking = new Cooking(null, orderId, null);
        Cooking createdCooking = new Cooking("cooking123", orderId, StatusOrder.RECEBIDO);
        CreateCookingResponse response = new CreateCookingResponse("cooking123", orderId, StatusOrder.RECEBIDO);

        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(cooking);
        when(createCookingUseCase.createCooking(any(Cooking.class))).thenReturn(createdCooking);
        when(cookingDTOMapper.createToResponse(createdCooking)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/cooking/receive-order/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cooking123"))
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.status").value("RECEBIDO"));

        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(createCookingUseCase).createCooking(any(Cooking.class));
        verify(cookingDTOMapper).createToResponse(createdCooking);
    }

    @Test
    void receiveOrder_whenUseCaseThrowsException_shouldReturnErrorResponse() throws Exception {
        // Arrange
        String orderId = "order123";
        Cooking cooking = new Cooking(null, orderId, null);

        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(cooking);
        when(createCookingUseCase.createCooking(any(Cooking.class)))
                .thenThrow(new CookingOperationException("Order not found"));

        // Act & Assert
        mockMvc.perform(post("/api/cooking/receive-order/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Order not found"));

        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(createCookingUseCase).createCooking(any(Cooking.class));
        verify(cookingDTOMapper, never()).createToResponse(any(Cooking.class));
    }

    @Test
    void startPreparation_withValidId_shouldReturnUpdatedCooking() throws Exception {
        // Arrange
        String orderId = "order123";
        Cooking cooking = new Cooking(null, orderId, null);
        Cooking updatedCooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        CreateCookingResponse response = new CreateCookingResponse("cooking123", orderId, StatusOrder.PREPARACAO);

        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(cooking);
        when(startPreparationUseCase.updateCooking(any(Cooking.class))).thenReturn(updatedCooking);
        when(cookingDTOMapper.createToResponse(updatedCooking)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/cooking/start-preparation/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cooking123"))
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.status").value("PREPARACAO"));

        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(startPreparationUseCase).updateCooking(any(Cooking.class));
        verify(cookingDTOMapper).createToResponse(updatedCooking);
    }

    @Test
    void finishPreparation_withValidId_shouldReturnUpdatedCooking() throws Exception {
        // Arrange
        String orderId = "order123";
        Cooking cooking = new Cooking(null, orderId, null);
        Cooking updatedCooking = new Cooking("cooking123", orderId, StatusOrder.PRONTO);
        CreateCookingResponse response = new CreateCookingResponse("cooking123", orderId, StatusOrder.PRONTO);

        when(cookingDTOMapper.createRequestToDomain(orderId)).thenReturn(cooking);
        when(finishPreparationUseCase.updateCooking(any(Cooking.class))).thenReturn(updatedCooking);
        when(cookingDTOMapper.createToResponse(updatedCooking)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/cooking/finish-preparation/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cooking123"))
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.status").value("PRONTO"));

        verify(cookingDTOMapper).createRequestToDomain(orderId);
        verify(finishPreparationUseCase).updateCooking(any(Cooking.class));
        verify(cookingDTOMapper).createToResponse(updatedCooking);
    }

    @Test
    void getAllCookings_withExistingCookings_shouldReturnCookingsList() throws Exception {
        // Arrange
        Cooking cooking1 = new Cooking("cooking1", "order1", StatusOrder.RECEBIDO);
        Cooking cooking2 = new Cooking("cooking2", "order2", StatusOrder.PREPARACAO);
        List<Cooking> cookings = Arrays.asList(cooking1, cooking2);

        CreateCookingResponse response1 = new CreateCookingResponse("cooking1", "order1", StatusOrder.RECEBIDO);
        CreateCookingResponse response2 = new CreateCookingResponse("cooking2", "order2", StatusOrder.PREPARACAO);

        when(getAllCookingsUseCase.execute()).thenReturn(cookings);
        when(cookingDTOMapper.createToResponse(cooking1)).thenReturn(response1);
        when(cookingDTOMapper.createToResponse(cooking2)).thenReturn(response2);

        // Act & Assert
        mockMvc.perform(get("/api/cooking")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("cooking1"))
                .andExpect(jsonPath("$[0].orderId").value("order1"))
                .andExpect(jsonPath("$[0].status").value("RECEBIDO"))
                .andExpect(jsonPath("$[1].id").value("cooking2"))
                .andExpect(jsonPath("$[1].orderId").value("order2"))
                .andExpect(jsonPath("$[1].status").value("PREPARACAO"));

        verify(getAllCookingsUseCase).execute();
        verify(cookingDTOMapper).createToResponse(cooking1);
        verify(cookingDTOMapper).createToResponse(cooking2);
    }

    @Test
    void getAllCookings_withNoCookings_shouldReturnNoContent() throws Exception {
        // Arrange
        when(getAllCookingsUseCase.execute()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/cooking")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(getAllCookingsUseCase).execute();
        verify(cookingDTOMapper, never()).createToResponse(any(Cooking.class));
    }

    @Test
    void getCookingByOrderId_withValidId_shouldReturnCooking() throws Exception {
        // Arrange
        String orderId = "order123";
        Cooking cooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        CreateCookingResponse response = new CreateCookingResponse("cooking123", orderId, StatusOrder.PREPARACAO);

        when(getCookingByOrderIdUseCase.execute(orderId)).thenReturn(cooking);
        when(cookingDTOMapper.createToResponse(cooking)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/cooking/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("cooking123"))
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.status").value("PREPARACAO"));

        verify(getCookingByOrderIdUseCase).execute(orderId);
        verify(cookingDTOMapper).createToResponse(cooking);
    }

    @Test
    void getCookingByOrderId_whenUseCaseThrowsException_shouldReturnErrorResponse() throws Exception {
        // Arrange
        String orderId = "nonExistentOrder";

        when(getCookingByOrderIdUseCase.execute(orderId))
                .thenThrow(new CookingOperationException("Cooking not found for order: " + orderId));

        // Act & Assert
        mockMvc.perform(get("/api/cooking/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cooking not found for order: " + orderId));

        verify(getCookingByOrderIdUseCase).execute(orderId);
        verify(cookingDTOMapper, never()).createToResponse(any(Cooking.class));
    }
}