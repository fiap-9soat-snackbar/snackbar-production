package com.snackbar.pickup.usecase;

import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotifyCustomerUseCaseImpl implements NotifyCustomerUseCase {

    private final PickupRepository pickupRepository;
    private final OrderClient orderClient;

    public NotifyCustomerUseCaseImpl(PickupRepository pickupRepository, OrderClient orderClient) {
        this.pickupRepository = pickupRepository;
        this.orderClient = orderClient;
    }

    @Override
    public void notify(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        
        Pickup pickup;
        Optional<Pickup> existingPickup = pickupRepository.findByOrderId(orderId);
        
        if (existingPickup.isPresent()) {
            pickup = existingPickup.get();
            pickup.setStatusPickup(StatusPickup.PRONTO);
            pickup.setNotifyCustomer(true);
        } else {
            pickup = new Pickup();
            pickup.setOrderId(orderId);
            pickup.setStatusPickup(StatusPickup.PRONTO);
            pickup.setNotifyCustomer(true);
        }

        pickupRepository.save(pickup);
        System.out.println("Pedido " + orderId + " está PRONTO, Cliente notificado");

        // Atualiza o status da order no order-service
        orderClient.updateOrderStatus(orderId, new StatusUpdateRequest("PRONTO"));
    }
}
