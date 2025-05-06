package com.snackbar.pickup.usecase;

import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.springframework.stereotype.Service;
import com.snackbar.pickup.client.OrderClient;

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
        Pickup pickup = new Pickup();
        pickup.setOrderId(orderId);
        pickup.setStatusPickup(StatusPickup.PRONTO);
        pickup.setNotifyCustomer(true);

        // Save status update in Pickup Collection
        pickupRepository.save(pickup);
        System.out.println("Pedido " + orderId + " está PRONTO, Cliente notificado");

        // Update status in Order Collection via OrderClient (Feign)
        orderClient.updateOrderStatus(orderId, "PRONTO");
    }
}
