package com.snackbar.cooking.infrastructure.gateways;

import com.snackbar.cooking.domain.entity.Cooking;
import com.snackbar.cooking.domain.entity.StatusOrder;
import com.snackbar.cooking.infrastructure.persistence.CookingEntity;
import com.snackbar.cooking.infrastructure.persistence.CookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CookingRepositoryGatewayTest {

    @Mock
    private CookingRepository cookingRepository;

    @Mock
    private CookingEntityMapper cookingMapper;

    @InjectMocks
    private CookingRepositoryGateway cookingRepositoryGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCooking_shouldSaveAndReturnCooking() {
        // Arrange
        Cooking cooking = new Cooking(null, "order123", StatusOrder.RECEBIDO);
        CookingEntity entity = new CookingEntity("cooking123", "order123", StatusOrder.RECEBIDO);
        
        CookingEntity savedEntity = new CookingEntity("cooking123", "order123", StatusOrder.RECEBIDO);
        
        Cooking expectedCooking = new Cooking("cooking123", "order123", StatusOrder.RECEBIDO);
        
        when(cookingMapper.toEntity(cooking)).thenReturn(entity);
        when(cookingRepository.save(entity)).thenReturn(savedEntity);
        when(cookingMapper.toDomainObj(savedEntity)).thenReturn(expectedCooking);
        
        // Act
        Cooking result = cookingRepositoryGateway.createCooking(cooking);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedCooking, result);
        
        verify(cookingMapper).toEntity(cooking);
        verify(cookingRepository).save(entity);
        verify(cookingMapper).toDomainObj(savedEntity);
    }

    @Test
    void updateCookingStatus_whenCookingExists_shouldUpdateAndReturnCooking() {
        // Arrange
        String orderId = "order123";
        StatusOrder newStatus = StatusOrder.PRONTO;
        
        CookingEntity existingEntity = new CookingEntity("cooking123", orderId, StatusOrder.PREPARACAO);
        
        CookingEntity updatedEntity = new CookingEntity("cooking123", orderId, newStatus);
        
        Cooking expectedCooking = new Cooking("cooking123", orderId, newStatus);
        
        when(cookingRepository.findByOrderId(orderId)).thenReturn(existingEntity);
        when(cookingRepository.save(any(CookingEntity.class))).thenReturn(updatedEntity);
        when(cookingMapper.toDomainObj(updatedEntity)).thenReturn(expectedCooking);
        
        // Act
        Cooking result = cookingRepositoryGateway.updateCookingStatus(orderId, newStatus);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedCooking, result);
        
        verify(cookingRepository).findByOrderId(orderId);
        verify(cookingRepository).save(any(CookingEntity.class));
        verify(cookingMapper).toDomainObj(updatedEntity);
    }

    @Test
    void updateCookingStatus_whenCookingDoesNotExist_shouldThrowException() {
        // Arrange
        String orderId = "nonExistentOrder";
        StatusOrder newStatus = StatusOrder.PRONTO;
        
        when(cookingRepository.findByOrderId(orderId)).thenReturn(null);
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cookingRepositoryGateway.updateCookingStatus(orderId, newStatus);
        });
        
        assertEquals("Cooking not found for orderId: " + orderId, exception.getMessage());
        verify(cookingRepository).findByOrderId(orderId);
        verify(cookingRepository, never()).save(any(CookingEntity.class));
    }

    @Test
    void findByOrderId_whenCookingExists_shouldReturnCooking() {
        // Arrange
        String orderId = "order123";
        
        CookingEntity entity = new CookingEntity("cooking123", orderId, StatusOrder.PREPARACAO);
        
        Cooking expectedCooking = new Cooking("cooking123", orderId, StatusOrder.PREPARACAO);
        
        when(cookingRepository.findByOrderId(orderId)).thenReturn(entity);
        when(cookingMapper.toDomainObj(entity)).thenReturn(expectedCooking);
        
        // Act
        Cooking result = cookingRepositoryGateway.findByOrderId(orderId);
        
        // Assert
        assertNotNull(result);
        assertEquals(expectedCooking, result);
        
        verify(cookingRepository).findByOrderId(orderId);
        verify(cookingMapper).toDomainObj(entity);
    }

    @Test
    void findAll_shouldReturnAllCookings() {
        // Arrange
        CookingEntity entity1 = new CookingEntity("cooking1", "order1", StatusOrder.RECEBIDO);
        
        CookingEntity entity2 = new CookingEntity("cooking2", "order2", StatusOrder.PREPARACAO);
        
        List<CookingEntity> entities = Arrays.asList(entity1, entity2);
        
        Cooking cooking1 = new Cooking("cooking1", "order1", StatusOrder.RECEBIDO);
        Cooking cooking2 = new Cooking("cooking2", "order2", StatusOrder.PREPARACAO);
        
        when(cookingRepository.findAll()).thenReturn(entities);
        when(cookingMapper.toDomainObj(entity1)).thenReturn(cooking1);
        when(cookingMapper.toDomainObj(entity2)).thenReturn(cooking2);
        
        // Act
        List<Cooking> result = cookingRepositoryGateway.findAll();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cooking1, result.get(0));
        assertEquals(cooking2, result.get(1));
        
        verify(cookingRepository).findAll();
        verify(cookingMapper).toDomainObj(entity1);
        verify(cookingMapper).toDomainObj(entity2);
    }
}