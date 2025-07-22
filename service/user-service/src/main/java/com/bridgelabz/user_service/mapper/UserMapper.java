package com.bridgelabz.user_service.mapper;


import com.bridgelabz.user_service.dto.RegisterRequestDTO;
import com.bridgelabz.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(RegisterRequestDTO req, String encodedPassword, String otp) {
        return User.builder()
                .firstname(req.firstname())
                .lastname(req.lastname())
                .email(req.email())
                .password(encodedPassword)
                .verified(false)
                .otp(otp)
                .build();
    }
}

