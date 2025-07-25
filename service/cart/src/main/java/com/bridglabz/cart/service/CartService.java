package com.bridglabz.cart.service;

import com.bridglabz.cart.dto.AddToCartRequest;
import com.bridglabz.cart.dto.CartResponse;
import com.bridglabz.cart.dto.UpdateCartRequest;

public interface CartService {
    void addToCart(Long userId, AddToCartRequest request);
    CartResponse getCart(Long userId);
    void updateCartItem(Long userId, UpdateCartRequest request);
    void removeCartItem(Long userId, Integer productId);
    void clearCart(Long userId);
}
