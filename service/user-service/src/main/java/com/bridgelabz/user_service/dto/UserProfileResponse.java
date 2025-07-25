package com.bridgelabz.user_service.dto;

import com.bridgelabz.user_service.entity.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
}

