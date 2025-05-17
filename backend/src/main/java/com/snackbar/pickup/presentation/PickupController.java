package com.snackbar.pickup.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> customerNotify(@PathVariable String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        notifyCustomerUseCase.notify(orderId);
        return ResponseEntity.ok("Cliente notificado - Pedido: " + orderId);
    }

    // Add a handler for empty orderId
    @PostMapping("/notify")
    public ResponseEntity<String> customerNotifyNoOrderId() {
        throw new IllegalArgumentException("Order ID cannot be empty");
    }

    @PostMapping("/delivery/{orderId}")
    public ResponseEntity<String> deliveryOrder(@PathVariable String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        deliveryPickupUseCase.delivery(orderId);
        return ResponseEntity.ok("Pedido " + orderId + " está Finalizado!");
    }
    
    // Add a handler for empty orderId
    @PostMapping("/delivery")
    public ResponseEntity<String> deliveryOrderNoOrderId() {
        throw new IllegalArgumentException("Order ID cannot be empty");
    }
    
    @GetMapping("/ready/{orderId}")
    public ResponseEntity<Boolean> isOrderReady(@PathVariable String orderId) {
        boolean isReady = isReadyPickupUseCase.isReady(orderId);
        return ResponseEntity.ok(isReady);
    }
    
    @GetMapping("/done/{orderId}")
    public ResponseEntity<Boolean> isOrderDone(@PathVariable String orderId) {
        boolean isDone = isReadyPickupUseCase.isDone(orderId);
        return ResponseEntity.ok(isDone);
    }
}
