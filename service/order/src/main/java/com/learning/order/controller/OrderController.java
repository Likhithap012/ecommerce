package com.learning.order.controller;


import java.util.List;

import com.learning.order.dto.OrderRequest;
import com.learning.order.dto.OrderResponse;
import com.learning.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(this.service.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(this.service.findAllOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable("order-id") Integer orderId) {
        return ResponseEntity.ok(this.service.findById(orderId));
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity<String> cancelOrder(@PathVariable("order-id") Integer orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok(" Order with ID " + orderId + " has been deleted successfully.");
    }

    @GetMapping("/customer/{customer-id}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomer(
            @PathVariable("customer-id") Integer customerId) {
        return ResponseEntity.ok(service.findOrdersByCustomerId(customerId));
    }

}
