package com.example.authentification;

import com.example.authentification.appuser.AppUser;
import com.example.authentification.appuser.AppUserRepository;
import com.example.authentification.appuser.AppUserService;
import com.example.authentification.registration.token.ConfirmationTokenService;
import com.example.authentification.util.FeignClientBucketManager;
import com.example.authentification.util.FeignClientUserMetadataDbManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private FeignClientBucketManager bucketFeign;

    @Mock
    private FeignClientUserMetadataDbManager userMetadataFeign;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    public void testLoadUserByUsername() {
        // Setup
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");

        Mockito.when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));

        // Execution
        UserDetails userDetails = appUserService.loadUserByUsername(appUser.getEmail());

        // Assertion
        Assert.assertEquals(userDetails.getUsername(), appUser.getEmail());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_UserNotFound() {
        // Setup
        String email = "test@example.com";
        Mockito.when(appUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Execution
        appUserService.loadUserByUsername(email);

        // Assertion (expect an exception to be thrown)
    }

    @Test
    public void testSignUpUser() {
        // Setup
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        Mockito.when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(bCryptPasswordEncoder.encode(appUser.getPassword())).thenReturn("encodedPassword");

        // Execution
        String token = appUserService.signUpUser(appUser);

        // Assertion
        Assert.assertNotNull(token);
    }

    @Test(expected = IllegalStateException.class)
    public void testSignUpUser_UserAlreadyExists() {
        // Setup
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");

        Mockito.when(appUserRepository.findByEmail(appUser.getEmail())).thenReturn(Optional.of(appUser));

        // Execution
        appUserService.signUpUser(appUser);

        // Assertion (expect an exception to be thrown)
    }

    @Test
    public void testEnableAppUser() {
        // Setup
        String email = "test@example.com";
        Mockito.when(appUserRepository.enableAppUser(email)).thenReturn(1);

        // Execution
        int result = appUserService.enableAppUser(email);

        // Assertion
        Assert.assertEquals(result, 1);
    }

}
