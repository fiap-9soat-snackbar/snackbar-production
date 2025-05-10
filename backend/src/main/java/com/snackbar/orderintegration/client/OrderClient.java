package com.snackbar.orderintegration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.snackbar.orderintegration.client.*;
import com.snackbar.orderintegration.dto.*;

@FeignClient(name = "order", url = "${ORDER_SERVICE_URL:http://localhost:8080}")
public interface OrderClient {

    @GetMapping("/api/orders/{orderId}")
    OrderResponse getOrder(@PathVariable String orderId);

    @PutMapping("/api/ordersrefactored/{orderId}/status")
    void updateOrderStatus(@PathVariable String orderId, @RequestBody StatusUpdateRequest request);
}
