package com.snackbar.cooking.client;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    String id,
    String orderNumber,
    LocalDateTime orderDateTime,
    String cpf,
    String name,
    List<OrderItemResponse> items,
    String statusOrder,
    String paymentMethod,
    Integer totalPrice,
    Integer remainingTime,
    Integer waitingTime
) {}