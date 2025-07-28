package com.bridglabz.cart.dto;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Double price
) {}