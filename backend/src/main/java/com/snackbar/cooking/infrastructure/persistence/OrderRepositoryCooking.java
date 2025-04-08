package com.snackbar.cooking.infrastructure.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepositoryCooking extends MongoRepository<OrderEntityCooking, String> {}