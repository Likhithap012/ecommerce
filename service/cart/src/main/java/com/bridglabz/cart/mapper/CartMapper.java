package com.bridglabz.cart.mapper;

import com.bridglabz.cart.dto.AddToCartRequest;
import com.bridglabz.cart.dto.CartItemResponse;
import com.bridglabz.cart.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartItem toEntity(AddToCartRequest request) {
        return CartItem.builder()
                .customerId(request.customerId())
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
    }

    public CartItemResponse toResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProductId(),
                item.getQuantity()
        );
    }

}
