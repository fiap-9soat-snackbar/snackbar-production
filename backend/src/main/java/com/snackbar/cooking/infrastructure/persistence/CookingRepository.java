package com.snackbar.cooking.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CookingRepository extends MongoRepository<CookingEntity, String> {
    // @Query("{ 'orderId' : ?0 }")
    CookingEntity findByOrderId(String orderId);
}