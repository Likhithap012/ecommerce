package com.bridglabz.cart.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(
        @NotNull(message = "Customer ID is required") Integer customerId,
        @NotNull(message = "Product ID is required") Integer productId,
        @Min(value = 1, message = "Quantity must be at least 1") Integer quantity
) {}