package com.example.authentification.registration;


import com.example.authentification.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/${spring.datasource.db_name}")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public String register (@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping("/token")
   public String getToken(@RequestBody LoginRequest loginRequest){
       Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        if(authenticate.isAuthenticated()){
        return registrationService.generateToken(loginRequest.getUsername());}
        else{
            throw new RuntimeException("invalid access");
        }
   }

    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String token){
        registrationService.validateToken(token);
        return "Token is valid";
    }


}
