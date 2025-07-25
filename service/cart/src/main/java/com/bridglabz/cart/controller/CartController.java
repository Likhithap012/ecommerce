package com.bridglabz.cart.controller;

import com.bridglabz.cart.dto.AddToCartRequest;
import com.bridglabz.cart.dto.CartItemResponse;
import com.bridglabz.cart.dto.CartResponse;
import com.bridglabz.cart.dto.UpdateCartRequest;
import com.bridglabz.cart.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest request,
                                            @RequestHeader("X-User-Id") String email) {
        cartService.addToCart(email, request);
        return ResponseEntity.ok("Product added to cart");
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody UpdateCartRequest request,
                                             @RequestHeader("X-User-Id") Long userId) {
        cartService.updateCartItem(userId, request);
        return ResponseEntity.ok("Cart item updated");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeItem(@PathVariable Integer productId,
                                             @RequestHeader("X-User-Id") Long userId) {
        cartService.removeCartItem(userId, productId);
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}
