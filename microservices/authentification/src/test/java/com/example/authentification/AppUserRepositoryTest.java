package com.example.authentification;

import com.example.authentification.appuser.AppUser;
import com.example.authentification.appuser.AppUserRepository;
import com.example.authentification.appuser.AppUserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserRepositoryTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        AppUser appUser = new AppUser("Oumaima", "ab", email, "password", AppUserRole.USER);
        when(appUserRepository.findByEmail(email)).thenReturn(Optional.of(appUser));

        // Act
        Optional<AppUser> result = appUserRepository.findByEmail(email);

        // Assert
        assertEquals(appUser, result.get());
    }

    @Test
    void testEnableAppUser() {

        String email = "test@example.com";
        when(appUserRepository.enableAppUser(email)).thenReturn(1);


        int result = appUserRepository.enableAppUser(email);


        assertEquals(1, result);
    }
}
