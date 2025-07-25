package com.bridglabz.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double total;
}