package com.example.authentification.registration;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/${spring.datasource.db_name}")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ObjectMapper objectMapper;
    @PostMapping
    public ResponseEntity<?> register (@RequestBody RegistrationRequest request){
        RegistrationResponse responseData = registrationService.register(request);

        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("email",responseData.getEmail())
                        .put("firstName",responseData.getFirstName())
                        .put("lastName",responseData.getLastName())
        );
    }

}
