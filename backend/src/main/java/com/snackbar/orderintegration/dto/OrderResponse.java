package com.snackbar.orderintegration.dto;

import java.util.Objects;

public class OrderResponse {
    private String id;
    private String statusOrder;

    // Default constructor
    public OrderResponse() {
    }
    
    // Copy constructor
    public OrderResponse(OrderResponse other) {
        this.id = other.id;
        this.statusOrder = other.statusOrder;
    }

    // Getters e Setters
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponse that = (OrderResponse) o;
        return Objects.equals(id, that.id) && 
               Objects.equals(statusOrder, that.statusOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusOrder);
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id='" + id + '\'' +
                ", statusOrder='" + statusOrder + '\'' +
                '}';
    }
}
