package com.bridglabz.cart.controller;

import com.bridglabz.cart.dto.AddToCartRequest;
import com.bridglabz.cart.dto.CartItemResponse;
import com.bridglabz.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping
    public ResponseEntity<Integer> addToCart(@RequestBody @Valid AddToCartRequest request) {
        return ResponseEntity.ok(service.addToCart(request));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable Integer customerId) {
        return ResponseEntity.ok(service.getCartByCustomer(customerId));
    }

    @DeleteMapping("/clear/{cartItemId}")
    public ResponseEntity<String > removeItem(@PathVariable Integer cartItemId) {
        service.removeItem(cartItemId);
        return ResponseEntity.ok(cartItemId+" deleted successfully.");
    }
}
