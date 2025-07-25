package com.bridgelabz.user_service.controller;

import com.bridgelabz.user_service.dto.ProfileRequestDTO;
import com.bridgelabz.user_service.dto.UserProfileResponse;
import com.bridgelabz.user_service.entity.User;
import com.bridgelabz.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<Integer> createProfile(@RequestBody ProfileRequestDTO dto) {
        int userId= userService.createOrUpdateProfile(dto);
        return ResponseEntity.ok(userId);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<UserProfileResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Integer id) {
        UserProfileResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponse> updateUser(
            @PathVariable Integer id,
            @RequestBody User updatedUserData
    ) {
        UserProfileResponse updatedUser = userService.updateUser(id, updatedUserData);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully with ID: " + id);
    }

}
