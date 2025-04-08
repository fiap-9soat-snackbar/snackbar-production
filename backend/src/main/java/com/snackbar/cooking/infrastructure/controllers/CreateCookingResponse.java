package com.snackbar.cooking.infrastructure.controllers;

public record CreateCookingResponse(
    String id,
    String orderId,
    Enum status
) {}