package com.snackbar.pickup.usecase;

import com.snackbar.orderintegration.client.OrderClient;
import com.snackbar.orderintegration.dto.StatusUpdateRequest;
import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import org.springframework.stereotype.Service;

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

        pickupRepository.save(pickup);
        System.out.println("Pedido " + orderId + " está PRONTO, Cliente notificado");

        // Atualiza o status da order no order-service
        orderClient.updateOrderStatus(orderId, new StatusUpdateRequest("PRONTO"));
    }
}
