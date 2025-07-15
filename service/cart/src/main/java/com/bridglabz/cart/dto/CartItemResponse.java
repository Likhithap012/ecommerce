package com.bridglabz.cart.dto;

public record CartItemResponse(
        Integer id,
        Integer productId,
        Integer quantity) {}