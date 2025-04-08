package com.snackbar.cooking.domain.entity;

import com.snackbar.order.domain.model.StatusOrder;

public record Cooking(
    String id,
    String orderId,
    StatusOrder status
) {}

// public class Cooking {
//     private String id;
//     private String orderId;
//     private String status;
//     private Integer estimatedTime;

//     // Constructor
//     public Cooking(String id, String orderId, String status, Integer estimatedTime) {
//         this.id = id;
//         this.orderId = orderId;
//         this.status = status;
//         this.estimatedTime = estimatedTime;
//     }

//     // Getters and Setters
//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public String getOrderId() {
//         return orderId;
//     }

//     public void setOrderId(String orderId) {
//         this.orderId = orderId;
//     }

//     public String getStatus() {
//         return status;
//     }

//     public void setStatus(String status) {
//         this.status = status;
//     }

//     public Integer getEstimatedTime() {
//         return estimatedTime;
//     }

//     public void setEstimatedTime(Integer estimatedTime) {
//         this.estimatedTime = estimatedTime;
//     }
// }
