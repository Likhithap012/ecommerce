package com.bridgelabz.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
@Embeddable
public class Address {

    private String street;
    private String houseNumber;
    private String pinCode;
    private String city;
    private String state;
}
