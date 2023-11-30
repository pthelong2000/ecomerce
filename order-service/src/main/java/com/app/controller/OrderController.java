package com.app.controller;

import com.app.model.OrderRequest;
import com.app.model.OrderResponse;
import com.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest request) {
        long orderId = orderService.placeOrder(request);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
