package com.snackbar.cooking.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
    String id,
    String name,
    String orderNumber,
    LocalDateTime orderDateTime,
    List<OrderItem> items,
    String statusOrder,
    Integer waitingTime,
    Integer remainingWaitingTime
) {}

