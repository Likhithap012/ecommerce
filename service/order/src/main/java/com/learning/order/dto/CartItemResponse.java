package com.learning.order.dto;

public record CartItemResponse(
        Integer id,
        Integer productId,
        Integer quantity
) {}