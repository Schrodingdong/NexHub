package com.example.authentification;

import com.example.authentification.appuser.AppUser;
import com.example.authentification.appuser.AppUserRole;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AppUserTest {

    @Test
    public void testAppUserConstructor() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password";
        AppUserRole appUserRole = AppUserRole.USER;

        AppUser appUser = new AppUser(firstName, lastName, email, password, appUserRole);

        assertEquals(firstName, appUser.getFirstName());
        assertEquals(lastName, appUser.getLastName());
        assertEquals(email, appUser.getUsername());
        assertEquals(password, appUser.getPassword());
        assertEquals(appUserRole, appUser.getAppUserRole());
        assertTrue(appUser.isAccountNonLocked());
        assertTrue(appUser.isEnabled());
    }

    @Test
    public void testGetAuthorities() {
        AppUserRole appUserRole = AppUserRole.USER;
        AppUser appUser = new AppUser("Oumaima", "ab", "oumaima.ab@example.com", "password", appUserRole);

        assertEquals(Collections.singleton(new SimpleGrantedAuthority(appUserRole.name())), appUser.getAuthorities());
    }

    @Test
    public void testUserDetailsMethods() {
        AppUserRole appUserRole = AppUserRole.USER;
        AppUser appUser = new AppUser("Oumaima", "ab", "oumaima.ab@example.com", "password", appUserRole);

        assertTrue(appUser.isAccountNonExpired());
        assertTrue(appUser.isAccountNonLocked());
        assertTrue(appUser.isCredentialsNonExpired());
        assertTrue(appUser.isEnabled());
    }
}

