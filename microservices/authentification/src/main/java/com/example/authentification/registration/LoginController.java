package com.example.authentification.registration;

import com.example.authentification.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LoginController {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        UserDetails appUser = appUserService.loadUserByUsername(credentials.getUsername());
        if (appUser == null || !passwordEncoder.matches(credentials.getPassword(), appUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        };

        // successful authentication
        return ResponseEntity.ok().build();
    }
    @PostMapping("/token")
    public String getToken(@RequestBody LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(loginRequest.getUsername());}
        else{
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token){
        jwtService.validateToken(token);
        return "Token is valid";
    }

}
