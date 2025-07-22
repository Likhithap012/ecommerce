package com.bridgelabz.product.controller;

import com.bridgelabz.product.dto.ProductPurchaseRequest;
import com.bridgelabz.product.dto.ProductPurchaseResponse;
import com.bridgelabz.product.dto.ProductRequest;
import com.bridgelabz.product.dto.ProductResponse;
import com.bridgelabz.product.exception.ProductAlreadyExistsException;
import com.bridgelabz.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request ) throws ProductAlreadyExistsException {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<String> updateProduct(@PathVariable("product-id") Integer productId,
                                                @RequestBody @Valid ProductRequest request) {
        service.updateProduct(productId, request);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product-id") Integer productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/{product-id}/reduce")
    public ResponseEntity<String> reduceStock(@PathVariable("product-id") Integer productId,
                                              @RequestParam("quantity") Integer quantity) {
        service.reduceStock(productId, quantity);
        return ResponseEntity.ok("Stock reduced successfully");
    }

    @PutMapping("/{product-id}/increase")
    public ResponseEntity<String> increaseStock(@PathVariable("product-id") Integer productId,
                                                @RequestParam("quantity") Integer quantity) {
        service.increaseStock(productId, quantity);
        return ResponseEntity.ok("Stock increased successfully");
    }

}
