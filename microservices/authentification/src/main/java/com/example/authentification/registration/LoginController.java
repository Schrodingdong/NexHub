package com.example.authentification.registration;

import com.example.authentification.appuser.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        UserDetails appUser = appUserService.loadUserByUsername(credentials.getEmail());
        if (appUser == null || !passwordEncoder.matches(credentials.getPassword(), appUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        String jwtToken = jwtService.generateToken(appUser.getUsername());
        LoginResponse loginResponse = new LoginResponse(
                credentials.getEmail(),
                jwtToken
        );

        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("email",loginResponse.getEmail())
                        .put("jwtToken",loginResponse.getJwtToken())
        );
    }



    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        if(authenticate.isAuthenticated()){
            return ResponseEntity.ok().body(
                    objectMapper.createObjectNode()
                            .put("jwtToken",jwtService.generateToken(loginRequest.getEmail()))
            );
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token){
        try{
            jwtService.validateToken(token);
            return ResponseEntity.ok().body("Token is valid");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

}
