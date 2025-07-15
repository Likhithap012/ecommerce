package com.learning.order.client;

import com.learning.order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/v1/products/{id}")
    ProductResponse getProductById(@PathVariable Integer id);

    @PutMapping("/api/v1/products/{id}/reduce")
    void reduceStock(@PathVariable Integer id, @RequestParam("quantity") Integer quantity);

    @PutMapping("/api/v1/products/{id}/increase")
    void increaseStock(@PathVariable Integer id, @RequestParam("quantity") Integer quantity);
}