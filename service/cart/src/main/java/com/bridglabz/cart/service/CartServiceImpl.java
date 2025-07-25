package com.bridglabz.cart.service;

import com.bridglabz.cart.client.ProductClient;
import com.bridglabz.cart.dto.*;
import com.bridglabz.cart.entity.Cart;
import com.bridglabz.cart.entity.CartItem;
import com.bridglabz.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    public void addToCart(Long userId, AddToCartRequest request) {
        ProductResponse product = productClient.getProductById(request.getProductId());
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(null, userId, new ArrayList<>()));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.getQuantity());
        } else {
            cart.getItems().add(new CartItem(null, product.getId(), request.getQuantity(), product.getPrice()));
        }

        cartRepository.save(cart);
    }

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart(null, userId, new ArrayList<>()));

        List<CartItemResponse> responses = cart.getItems().stream().map(item -> {
            ProductResponse product = productClient.getProductById(item.getProductId());
            double total = item.getPrice() * item.getQuantity();
            return new CartItemResponse(item.getProductId(), product.getName(), item.getQuantity(), item.getPrice(), total);
        }).toList();

        double grandTotal = responses.stream().mapToDouble(CartItemResponse::getTotal).sum();
        return new CartResponse(userId, responses, grandTotal);
    }

    @Override
    public void updateCartItem(Long userId, UpdateCartRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        item.setQuantity(request.getQuantity());
        cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long userId, Integer productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
