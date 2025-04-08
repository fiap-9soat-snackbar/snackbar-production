package com.snackbar.cooking.infrastructure.controllers;

public record GetCookingResponse(
    String id,
    String orderId,
    Enum status
) {}