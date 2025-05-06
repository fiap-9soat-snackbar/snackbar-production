package com.snackbar.cooking.infrastructure.persistence;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.snackbar.cooking.domain.entity.StatusOrder;

@Document(collection = "cookings")
public class CookingEntity {

    @Id
    private String id;

    private String orderId;
    
    private StatusOrder status;

    public CookingEntity(String id, String orderId, StatusOrder status) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }
}