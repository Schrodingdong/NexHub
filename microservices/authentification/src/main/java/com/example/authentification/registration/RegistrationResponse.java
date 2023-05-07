package com.example.authentification.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String token;
}
