package com.snackbar.pickup.usecase;

import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.springframework.stereotype.Service;
import com.snackbar.orderRefactory.application.usecases.OrderUseCase;

@Service
public class DeliveryPickupUseCaseImpl implements DeliveryPickupUseCase {

    private final PickupRepository pickupRepository;
    private final OrderUseCase orderUseCase;


    public DeliveryPickupUseCaseImpl(PickupRepository pickupRepository, OrderUseCase orderUseCase) {
        this.pickupRepository = pickupRepository;
        this.orderUseCase = orderUseCase;

    }

    @Override
    public void delivery(String orderId) {
        // Search for Pickup associated with OrderID
        Pickup pickup = pickupRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Este pedido não foi retirado: " + orderId));

        // Update status to FINALIZADO
        pickup.setStatusPickup(StatusPickup.FINALIZADO);

        // Save status update in Pickup Collection
        pickupRepository.save(pickup);
        System.out.println("Pedido " + orderId + " foi Finalizado");

        // Update status in Order Collection
        orderUseCase.updateStatusOrder(orderId,"FINALIZADO");
    }
}
