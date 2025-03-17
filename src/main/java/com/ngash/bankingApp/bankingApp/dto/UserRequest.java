package com.ngash.bankingApp.bankingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String firstName;
    private String lastName;
    private String gender;
    private String stateOfOrigin;
    private String address;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
}
