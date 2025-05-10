package com.snackbar.pickup.usecase;

import com.snackbar.pickup.client.OrderClient;
import com.snackbar.pickup.client.*;
import com.snackbar.pickup.dto.StatusUpdateRequest;
import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPickupUseCaseImpl implements DeliveryPickupUseCase {

    private final PickupRepository pickupRepository;
    private final OrderClient orderClient;

    public DeliveryPickupUseCaseImpl(PickupRepository pickupRepository, OrderClient orderClient) {
        this.pickupRepository = pickupRepository;
        this.orderClient = orderClient;
    }

    @Override
    public void delivery(String orderId) {
        Pickup pickup = pickupRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Este pedido não foi retirado: " + orderId));

        pickup.setStatusPickup(StatusPickup.FINALIZADO);
        pickupRepository.save(pickup);
        System.out.println("Pedido " + orderId + " foi Finalizado");

        orderClient.updateOrderStatus(orderId, new StatusUpdateRequest("FINALIZADO"));
    }
}
