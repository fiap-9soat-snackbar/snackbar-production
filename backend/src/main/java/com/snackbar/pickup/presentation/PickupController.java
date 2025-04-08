package com.snackbar.pickup.presentation;

import org.springframework.web.bind.annotation.*;
import com.snackbar.pickup.usecase.DeliveryPickupUseCase;
import com.snackbar.pickup.usecase.NotifyCustomerUseCase;

@RestController
@RequestMapping("/api/pickup")
public class PickupController {

    private final NotifyCustomerUseCase notifyCustomerUseCase;
    private final DeliveryPickupUseCase deliveryPickupUseCase;

    public PickupController(NotifyCustomerUseCase notifyCustomerUseCase, DeliveryPickupUseCase deliveryPickupUseCase) {
        this.notifyCustomerUseCase = notifyCustomerUseCase;
        this.deliveryPickupUseCase = deliveryPickupUseCase;
    }

    @PostMapping("/notify/{orderId}")
    public String customerNotify(@PathVariable String orderId) {
        notifyCustomerUseCase.notify(orderId);
        return "Cliente notificado - Pedido: " + orderId;
    }

    @PostMapping("/delivery/{orderId}")
    public String deliveryOrder(@PathVariable String orderId) {
        deliveryPickupUseCase.delivery(orderId);
        return "Pedido " + orderId + " está Finalizado!";
    }
}
