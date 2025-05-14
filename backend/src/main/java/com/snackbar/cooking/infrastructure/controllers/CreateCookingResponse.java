package com.snackbar.cooking.infrastructure.controllers;

import com.snackbar.cooking.domain.entity.StatusOrder;

public record CreateCookingResponse(
    String id,
    String orderId,
    StatusOrder status
) {}