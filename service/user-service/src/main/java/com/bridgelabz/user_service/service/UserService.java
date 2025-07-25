package com.bridgelabz.user_service.service;

import com.bridgelabz.user_service.dto.ProfileRequestDTO;

import com.bridgelabz.user_service.dto.UserProfileResponse;
import com.bridgelabz.user_service.entity.User;
import com.bridgelabz.user_service.mapper.UserMapper;
import com.bridgelabz.user_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Create or update profile
    public Integer createOrUpdateProfile(ProfileRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .map(existingUser -> userMapper.updateUserFromDTO(dto, existingUser))
                .orElseGet(() -> userMapper.toUser(dto));

        userRepository.save(user);
        return user.getId();
    }

    // Get all users
    public List<UserProfileResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserProfileResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return UserMapper.toResponse(user);
    }

    // Update profile
    public UserProfileResponse updateUser(Integer id, User updatedUserData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setFirstname(updatedUserData.getFirstname());
        user.setLastname(updatedUserData.getLastname());
        user.setEmail(updatedUserData.getEmail());

        if (updatedUserData.getAddress() != null) {
            user.setAddress(updatedUserData.getAddress());
        }

        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userRepository.delete(user);
    }


}
