package com.learning.order.controller;

import com.learning.order.dto.OrderPreviewResponse;
import com.learning.order.dto.OrderRequest;
import com.learning.order.dto.OrderSuccessResponse;
import com.learning.order.dto.OrderSummaryResponse;
import com.learning.order.entity.Order;
import com.learning.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderSuccessResponse> placeOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping("/preview/{customerId}")
    public ResponseEntity<OrderPreviewResponse> previewOrder(@PathVariable Integer customerId) {
        return ResponseEntity.ok(orderService.previewOrder(customerId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("order cancelled successfully");
    }

    @GetMapping("/total/{orderId}")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getTotalAmount(orderId));
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderSummaryResponse>> getOrdersByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

}

