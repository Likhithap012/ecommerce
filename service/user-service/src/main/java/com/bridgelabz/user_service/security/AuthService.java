package com.bridgelabz.user_service.security;

import com.bridgelabz.user_service.dto.AuthResponseDTO;
import com.bridgelabz.user_service.dto.LoginRequestDTO;
import com.bridgelabz.user_service.dto.RegisterRequestDTO;
import com.bridgelabz.user_service.entity.User;
import com.bridgelabz.user_service.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequestDTO req) throws MessagingException {
        if (userRepo.existsByEmail(req.email())) {
            throw new RuntimeException("Email already registered");
        }

        String otp = generateOtp();
        User user = User.builder()
                .firstname(req.firstname())
                .lastname(req.lastname())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .verified(false)
                .otp(otp)
                .build();

        userRepo.save(user);

        String body = "Welcome " + req.firstname() + "! Your OTP is: " + otp;
        emailService.sendEmail(user.getEmail(), "Verify your account", body);

        return "Registered successfully. Check your email for OTP.";
    }


    public AuthResponseDTO loginWithPassword(LoginRequestDTO request) {
        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getFirstname());
    }

    public AuthResponseDTO loginWithOtp(String email, String otp) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOtp().equals(otp)) throw new RuntimeException("Invalid OTP.");

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getFirstname());
    }

    public boolean validateToken(String token) {
        return jwtUtil.isValid(token);
    }

    public User getUserFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}