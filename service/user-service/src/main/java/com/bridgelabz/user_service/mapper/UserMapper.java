package com.bridgelabz.user_service.mapper;

import com.bridgelabz.user_service.dto.ProfileRequestDTO;
import com.bridgelabz.user_service.dto.UserProfileResponse;
import com.bridgelabz.user_service.entity.Address;
import com.bridgelabz.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Map DTO to new User
    public User toUser(ProfileRequestDTO dto) {
        Address address = Address.builder()
                .street(dto.getAddress().getStreet())
                .houseNumber(dto.getAddress().getHouseNumber())
                .pinCode(dto.getAddress().getPinCode())
                .city(dto.getAddress().getCity())
                .state(dto.getAddress().getState())
                .build();

        return User.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .address(address)
                .build();
    }

    // Update existing User with values from DTO
    public User updateUserFromDTO(ProfileRequestDTO dto, User user) {
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());

        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
        }

        address.setStreet(dto.getAddress().getStreet());
        address.setHouseNumber(dto.getAddress().getHouseNumber());
        address.setPinCode(dto.getAddress().getPinCode());
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());

        user.setAddress(address);
        return user;
    }
    public static UserProfileResponse toResponse(User user) {
        UserProfileResponse dto = new UserProfileResponse();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setAddress(user.getAddress()); // or map to AddressResponse
        return dto;
    }

}
