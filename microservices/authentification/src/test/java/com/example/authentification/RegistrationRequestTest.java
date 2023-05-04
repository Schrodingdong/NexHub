package com.example.authentification;

import com.example.authentification.registration.RegistrationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegistrationRequestTest {

    @Test
    public void testConstructorAndGetters() {
        String firstName = "Oumaima";
        String lastName = "ab";
        String email = "oumaimaab@example.com";
        String password = "password";
        RegistrationRequest registrationRequest = new RegistrationRequest(firstName, lastName, email, password);
        Assertions.assertEquals(firstName, registrationRequest.getFirstName());
        Assertions.assertEquals(lastName, registrationRequest.getLastName());
        Assertions.assertEquals(email, registrationRequest.getEmail());
        Assertions.assertEquals(password, registrationRequest.getPassword());
    }
}
