package com.example.authentification.registration;


import com.example.authentification.appuser.AppUser;
import com.example.authentification.appuser.AppUserRole;
import com.example.authentification.appuser.AppUserService;
import com.example.authentification.email.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public RegistrationResponse register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail)
            throw new IllegalStateException("email not valid");

        RegistrationResponse registrationResponse = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
        return registrationResponse;
    }
}
