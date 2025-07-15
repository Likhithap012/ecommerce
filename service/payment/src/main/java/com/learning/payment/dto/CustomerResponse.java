package com.learning.payment.dto;

public record CustomerResponse(
        Integer id,
        String firstname,
        String lastname,
        String email
) {}
