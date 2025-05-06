package com.snackbar.cooking.client;

public record OrderItemResponse(
    String name,
    Double price,
    Integer quantity,
    Integer cookingTime,
    String customization
) {}