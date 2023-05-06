package com.example.authentification.registration;

import com.example.authentification.appuser.AppUser;
import com.example.authentification.appuser.AppUserService;
import org.apache.catalina.Authenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(AppUserService appUserService,BCryptPasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        UserDetails appUser = appUserService.loadUserByUsername(credentials.getUsername());
        if (appUser == null || !passwordEncoder.matches(credentials.getPassword(), appUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        };

        // successful authentication
        return ResponseEntity.ok().build();
    }}
