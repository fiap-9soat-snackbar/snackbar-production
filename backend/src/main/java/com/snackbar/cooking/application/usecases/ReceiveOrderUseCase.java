package com.snackbar.cooking.application.usecases;

import com.snackbar.cooking.application.gateways.OrderGateway;
import org.springframework.stereotype.Service;

@Service
public class ReceiveOrderUseCase {
    private final OrderGateway orderGateway;
    private static final String STATUS_PAID = "PAGO";
    private static final String STATUS_RECEIVED = "RECEBIDO";

    public ReceiveOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public String execute(String id) {
        var order = orderGateway.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        if (STATUS_PAID.equals(order.statusOrder())) {
            orderGateway.updateStatus(id, STATUS_RECEIVED);
            return "Pedido recebido";
        }

        return "The order is already in " + order.statusOrder() + " status";
    }
}