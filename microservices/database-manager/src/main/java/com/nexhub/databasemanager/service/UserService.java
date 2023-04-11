package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsersByName(String name){
        return userRepository.getUsersByName(name);
    }
    public User getUserById(@NotNull Long id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User saveUser(@NotNull User u) throws BadRequestException{
        boolean mailTaken = userRepository.isMailTaken(u.getMail());
        if (mailTaken){
            throw new BadRequestException("The email : " +u.getMail()+" is already taken :/");
        }
        userRepository.save(u);
        return u;
    }

    public User updateUser(@NotNull long userId, @NotNull User modifiedUser) throws BadRequestException{
        if(modifiedUser == null){
            throw new BadRequestException("No Input Data");
        }
        User selectedUser = getUserById(userId);
        if(selectedUser != null){
            String newUsername = modifiedUser.getUsername();
            String newMail = modifiedUser.getMail();

            selectedUser.setUsername(
                    (newUsername == null)? selectedUser.getUsername() : newUsername
            );
            selectedUser.setMail(
                    (newMail == null)? selectedUser.getMail() : newMail
            );
            return saveUser(selectedUser);
        } else {
            return saveUser(modifiedUser);
        }


    }

    public void deleteUser(long userId){
        userRepository.deleteById(userId);
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }

}
