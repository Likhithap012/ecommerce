package com.bridglabz.cart.service;

import com.bridglabz.cart.client.ProductClient;
import com.bridglabz.cart.dto.*;
import com.bridglabz.cart.entity.CartItem;
import com.bridglabz.cart.repository.CartRepository;
import com.bridglabz.cart.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Override
    public void addToCart(Long userId, AddToCartRequest request) {
        CartItem item = cartRepository.findByCustomerIdAndProductId(userId.intValue(), request.getProductId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + request.getQuantity());
                    return existing;
                })
                .orElse(CartItem.builder()
                        .customerId(userId.intValue())
                        .productId(request.getProductId())
                        .quantity(request.getQuantity())
                        .build());

        cartRepository.save(item);
    }

    @Override
    public CartResponse getCart(Long userId) {
        List<CartItem> items = cartRepository.findByCustomerId(userId.intValue());

        List<CartItemResponse> itemResponses = items.stream().map(cartItem -> {
            ProductResponse product = productClient.getProductById(cartItem.getProductId());
            return CartItemResponse.builder()
                    .productId(product.id())
                    .productName(product.name())
                    .price(product.price())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(product.price() * cartItem.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        double totalAmount = itemResponses.stream().mapToDouble(CartItemResponse::getTotalPrice).sum();

        return CartResponse.builder()
                .items(itemResponses)
                .totalAmount(totalAmount)
                .build();
    }

    @Override
    public void updateCartItem(Long userId, UpdateCartRequest request) {
        CartItem item = cartRepository.findByCustomerIdAndProductId(userId.intValue(), request.getProductId())
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        item.setQuantity(request.getQuantity());
        cartRepository.save(item);
    }

    @Override
    @Transactional
    public void removeCartItem(Long userId, Integer productId) {
        cartRepository.deleteByCustomerIdAndProductId(userId.intValue(), productId);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        cartRepository.deleteByCustomerId(userId.intValue());
    }
}
