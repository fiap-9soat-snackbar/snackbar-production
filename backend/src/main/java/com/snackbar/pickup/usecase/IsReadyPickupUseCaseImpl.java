package com.snackbar.pickup.usecase;

import com.snackbar.order.service.OrderService;
import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.springframework.stereotype.Service;

@Service
public class IsReadyPickupUseCaseImpl implements IsReadyPickupUseCase {

    private final PickupRepository pickupRepository;

    public IsReadyPickupUseCaseImpl(PickupRepository pickupRepository) {
        this.pickupRepository = pickupRepository;
    }

    @Override
    public boolean isReady(String orderId) {
        boolean isReady = pickupRepository.findByOrderId(orderId)
                .map(pickup -> pickup.getStatusPickup() == StatusPickup.PRONTO)
                .orElse(false);
        return isReady;
    }

    @Override
    public boolean isDone(String orderId) {
        boolean isDone = pickupRepository.findByOrderId(orderId)
                .map(pickup -> pickup.getStatusPickup() == StatusPickup.FINALIZADO)
                .orElse(false);
        return isDone;
    }
}
