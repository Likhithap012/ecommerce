package com.bridgelabz.user_service.controller;

import com.bridgelabz.user_service.dto.AuthResponseDTO;
import com.bridgelabz.user_service.dto.LoginRequestDTO;
import com.bridgelabz.user_service.dto.OtpLoginRequestDTO;
import com.bridgelabz.user_service.dto.RegisterRequestDTO;
import com.bridgelabz.user_service.security.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO req) throws MessagingException {
        String result = authService.register(req);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login/password")
    public ResponseEntity<AuthResponseDTO> loginWithPassword(@RequestBody @Valid LoginRequestDTO req) {
        return ResponseEntity.ok(authService.loginWithPassword(req));
    }

    @PostMapping("/login/otp")
    public ResponseEntity<AuthResponseDTO> loginWithOtp(@RequestBody @Valid OtpLoginRequestDTO req) {
        return ResponseEntity.ok(authService.loginWithOtp(req.email(), req.otp()));
    }

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authService.getUserFromToken(authHeader));
    }
}