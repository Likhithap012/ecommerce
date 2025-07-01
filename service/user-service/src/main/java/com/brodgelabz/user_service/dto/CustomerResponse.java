package com.brodgelabz.user_service.dto;


import com.brodgelabz.user_service.entity.Address;

public record CustomerResponse(
        Integer id,
        String firstname,
        String lastname,
        String email,
        Address address
) {

}
