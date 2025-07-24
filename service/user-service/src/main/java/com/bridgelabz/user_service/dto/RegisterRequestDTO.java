package com.bridgelabz.user_service.dto;


public record RegisterRequestDTO(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
