package com.snackbar.pickup.usecase;

import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
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
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        
        Pickup pickup = pickupRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Este pedido não foi retirado: " + orderId));

        pickup.setStatusPickup(StatusPickup.FINALIZADO);
        pickupRepository.save(pickup);
        System.out.println("Pedido " + orderId + " foi Finalizado");

        orderClient.updateOrderStatus(orderId, new StatusUpdateRequest("FINALIZADO"));
    }
}
