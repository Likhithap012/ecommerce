package com.learning.order.client;

import com.learning.order.dto.CartItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "cart-service")
public interface CartClient {
    @GetMapping("/api/v1/cart/{customerId}")
    List<CartItemResponse> getCartItems(@PathVariable Integer customerId);

    @DeleteMapping("/api/v1/cart/clear/{customerId}")
    void clearCart(@PathVariable Integer customerId);
}