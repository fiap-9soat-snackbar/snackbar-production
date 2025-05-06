package com.snackbar.cooking.client;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    String id,
    String name,
    String orderNumber,
    LocalDateTime orderDateTime,
    List<OrderItemResponse> items,
    String statusOrder,
    Integer waitingTime,
    Integer remainingWaitingTime
) {}