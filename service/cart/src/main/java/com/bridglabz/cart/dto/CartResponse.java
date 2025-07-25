package com.bridglabz.cart.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long userId;
    private List<CartItemResponse> items;
    private Double grandTotal;
}
