package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean userExists(@NotNull long userId){
        User user = userRepository.findByUserId(userId);
        return (user == null)? false : true;
    }
    public List<User> getUsersByName(String name){
        return userRepository.getUsersByName(name);
    }
    public User getUserById(@NotNull Long id){
        User user = userRepository.findByUserId(id);
        return user;
    }
    public User getUserByMail(@NotNull String mail){
        return userRepository.getUserByMail(mail);
    }
    public List<User> getAllUsers(){
        return userRepository.findAllUsers();
    }
    public List<User> getUserFollowers(@NotNull Long userId){
        return userRepository.userFollowers(userId);
    }
    public List<User> getUserFollowing(@NotNull Long userId){
        return userRepository.userFollowing(userId);
    }
    public User saveUser(@NotNull User u) throws BadRequestException{
        boolean mailTaken = userRepository.isMailTaken(u.getMail());
        if (mailTaken){
            throw new BadRequestException("The email : " +u.getMail()+" is already taken :/");
        }
        return userRepository.saveToGraph(u.getUsername(), u.getMail(), u.getBucketId());
    }
    public void followUser(@NotNull Long userId, @NotNull Long followId){
        userRepository.followUser(userId, followId);
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
                    (newUsername == null || newUsername.isEmpty())?
                            selectedUser.getUsername() :
                            newUsername
            );
            selectedUser.setMail(
                    (newMail == null || newMail.isEmpty())?
                            selectedUser.getMail() :
                            newMail
            );
            return userRepository.updateUser(selectedUser.getUserId(), selectedUser.getUsername(),selectedUser.getMail());
        } else {
            return userRepository.saveToGraph(modifiedUser.getUsername(),modifiedUser.getMail(), modifiedUser.getBucketId());
        }


    }
    public void deleteUser(long userId){
        userRepository.deleteById(userId);
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }

}
