package com.snackbar.pickup.client;

import com.snackbar.pickup.client.*;
import com.snackbar.pickup.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order", url = "${ORDER_SERVICE_URL:http://localhost:8080}")
public interface OrderClient {

    @GetMapping("/api/orders/{orderId}")
    OrderResponse getOrder(@PathVariable String orderId);

    @PutMapping("/api/ordersrefactored/{orderId}/status")
    void updateOrderStatus(@PathVariable String orderId, @RequestBody StatusUpdateRequest request);
}
