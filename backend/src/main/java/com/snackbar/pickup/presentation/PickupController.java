package com.snackbar.pickup.presentation;

import org.springframework.web.bind.annotation.*;
import com.snackbar.pickup.usecase.DeliveryPickupUseCase;
import com.snackbar.pickup.usecase.IsReadyPickupUseCase;
import com.snackbar.pickup.usecase.NotifyCustomerUseCase;

@RestController
@RequestMapping("/api/pickup")
public class PickupController {

    private final NotifyCustomerUseCase notifyCustomerUseCase;
    private final DeliveryPickupUseCase deliveryPickupUseCase;
    private final IsReadyPickupUseCase isReadyPickupUseCase;

    public PickupController(NotifyCustomerUseCase notifyCustomerUseCase, 
                           DeliveryPickupUseCase deliveryPickupUseCase,
                           IsReadyPickupUseCase isReadyPickupUseCase) {
        this.notifyCustomerUseCase = notifyCustomerUseCase;
        this.deliveryPickupUseCase = deliveryPickupUseCase;
        this.isReadyPickupUseCase = isReadyPickupUseCase;
    }

    @PostMapping("/notify/{orderId}")
    public String customerNotify(@PathVariable String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        notifyCustomerUseCase.notify(orderId);
        return "Cliente notificado - Pedido: " + orderId;
    }

    @PostMapping("/delivery/{orderId}")
    public String deliveryOrder(@PathVariable String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        deliveryPickupUseCase.delivery(orderId);
        return "Pedido " + orderId + " está Finalizado!";
    }
    
    @GetMapping("/ready/{orderId}")
    public boolean isOrderReady(@PathVariable String orderId) {
        return isReadyPickupUseCase.isReady(orderId);
    }
    
    @GetMapping("/done/{orderId}")
    public boolean isOrderDone(@PathVariable String orderId) {
        return isReadyPickupUseCase.isDone(orderId);
    }
}
