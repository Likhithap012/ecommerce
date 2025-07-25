package com.bridgelabz.user_service.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressDTO {
    private String street;
    private String houseNumber;
    private String pinCode;
    private String city;
    private String state;
}
