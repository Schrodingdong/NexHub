package com.example.authentification.appuser;

import com.example.authentification.registration.RegistrationResponse;
import com.example.authentification.registration.token.ConfirmationToken;
import com.example.authentification.registration.token.ConfirmationTokenService;
import com.example.authentification.util.FeignClientBucketManager;
import com.example.authentification.util.FeignClientUserMetadataDbManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Autowired
    private FeignClientBucketManager bucketFeign;
    @Autowired
    private FeignClientUserMetadataDbManager userMetadataFeign;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public RegistrationResponse signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        // save the user in the Metadata-db
        MetadataUserModel metadataUserModel = saveUserToMetadataDb(appUser);

        // save the associated userBucket
        bucketFeign.createNewBucket(metadataUserModel.getBucketId());

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return new RegistrationResponse(
                appUser.getEmail(),
                appUser.getFirstName(),
                appUser.getLastName(),
                token
        );
    }





    private MetadataUserModel saveUserToMetadataDb(AppUser appUser) {
        String accountUsername = appUser.getFirstName()+appUser.getLastName();
        MetadataUserModel metadataUserModel = new MetadataUserModel(
                accountUsername,
                appUser.getEmail()
        );
        userMetadataFeign.addNewUser(metadataUserModel);
        return metadataUserModel;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
