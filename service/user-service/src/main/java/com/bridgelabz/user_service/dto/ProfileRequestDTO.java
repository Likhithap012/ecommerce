package com.bridgelabz.user_service.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProfileRequestDTO {
    private String firstname;
    private String lastname;
    private String email;
    private AddressDTO address;
}
