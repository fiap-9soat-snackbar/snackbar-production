package com.snackbar.pickup.gateway;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.snackbar.pickup.entity.Pickup;

import java.util.Optional;

@Repository
public interface PickupRepository extends MongoRepository<Pickup, String> {
   
    Optional<Pickup> findByOrderId(String orderId);
}