package com.snackbar.cooking.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
public class OrderEntityCooking {
    @Id
    private String id;
    private String name;
    private String orderNumber;
    private LocalDateTime orderDateTime;
    private List<OrderItemEntity> itemEntities;
    private String statusOrder;
    private Integer waitingTime;
    private Integer remainingWaitingTime;

    // Constructor with all fields
    public OrderEntityCooking(String id, String name, String orderNumber, LocalDateTime orderDateTime, List<OrderItemEntity> itemEntities, String statusOrder, Integer waitingTime, Integer remainingWaitingTime) {
        this.id = id;
        this.name = name;
        this.orderNumber = orderNumber;
        this.orderDateTime = orderDateTime;
        this.itemEntities = itemEntities;
        this.statusOrder = statusOrder;
        this.waitingTime = waitingTime;
        this.remainingWaitingTime = remainingWaitingTime;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public List<OrderItemEntity> getItemEntities() {
        return itemEntities;
    }

    public void setItemEntities(List<OrderItemEntity> itemEntities) {
        this.itemEntities = itemEntities;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Integer getRemainingWaitingTime() {
        return remainingWaitingTime;
    }

    public void setRemainingWaitingTime(Integer remainingWaitingTime) {
        this.remainingWaitingTime = remainingWaitingTime;
    }
}
