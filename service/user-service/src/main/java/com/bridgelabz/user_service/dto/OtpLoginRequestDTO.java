package com.bridgelabz.user_service.dto;

public record OtpLoginRequestDTO(
        String email,
        String otp
) {}
