package com.snackbar.pickup.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pickup-order-service", url = "${order.service.url}")
public interface OrderClient {

    @GetMapping("/orders/{orderId}")
    OrderResponse getOrder(@PathVariable("orderId") String orderId);

    @PatchMapping("/orders/{orderId}/status")
    void updateOrderStatus(@PathVariable("orderId") String orderId, @RequestParam("status") String status);
}
