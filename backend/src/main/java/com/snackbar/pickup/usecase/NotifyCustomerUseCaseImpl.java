package com.snackbar.pickup.usecase;

import com.snackbar.pickup.entity.Pickup;
import com.snackbar.pickup.entity.StatusPickup;
import com.snackbar.pickup.gateway.PickupRepository;
import com.snackbar.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class NotifyCustomerUseCaseImpl implements NotifyCustomerUseCase {

    private final PickupRepository pickupRepository;
    private final OrderService orderService;

    public NotifyCustomerUseCaseImpl(PickupRepository pickupRepository, OrderService orderService) {
        this.pickupRepository = pickupRepository;
        this.orderService = orderService;
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

        // Update status in Order Collection
        orderService.updateStatusOrder(orderId);
    }
}
