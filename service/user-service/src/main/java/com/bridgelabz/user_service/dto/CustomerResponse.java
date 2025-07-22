package com.bridgelabz.user_service.dto;


import com.bridgelabz.user_service.entity.Address;

public record CustomerResponse(
        Integer id,
        String firstname,
        String lastname,
        String email,
        Address address
) {

}
