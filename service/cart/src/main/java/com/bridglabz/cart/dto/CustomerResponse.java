package com.bridglabz.cart.dto;

public record CustomerResponse(
        Integer id,
        String firstname,
        String lastname,
        String email
) {

}
