package com.snackbar.pickup.usecase;

public interface IsReadyPickupUseCase {
    boolean isReady(String orderId);
    boolean isDone(String orderId);
}
