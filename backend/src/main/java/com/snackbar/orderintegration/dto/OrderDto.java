package com.snackbar.orderintegration.dto;

import java.util.List;

public class OrderDto {
    private String id;
    private String statusOrder;


    // Getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }
}
