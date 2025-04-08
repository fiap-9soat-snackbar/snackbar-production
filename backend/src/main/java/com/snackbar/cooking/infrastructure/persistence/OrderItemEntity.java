package com.snackbar.cooking.infrastructure.persistence;

public class OrderItemEntity {
    private String name;
    private Double price;
    private Integer quantity;
    private Integer cookingTime;
    private String customization;

    // Constructor
    public OrderItemEntity(String name, Double price, Integer quantity,
                           Integer cookingTime, String customization) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cookingTime = cookingTime;
        this.customization = customization;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getCustomization() {
        return customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }
}