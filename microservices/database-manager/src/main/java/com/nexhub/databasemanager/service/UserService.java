package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ResourceService resourceService;

    public boolean userExists(@NotNull long userId){
        User user = userRepository.findByUserId(userId);
        return user != null;
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
        boolean mailTaken = userRepository.isMailTaken(u.getEmail());
        if (mailTaken){
            throw new BadRequestException("The email : " +u.getEmail()+" is already taken :/");
        }
        return userRepository.saveToGraph(
                u.getUsername(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getBucketId()
        );
    }
    public void followUser(@NotNull Long userId, @NotNull Long followId){
        userRepository.followUser(userId, followId);
    }
    public void unfollowUser(@NotNull Long userId, @NotNull Long toUnfollow){
        userRepository.unfollowUser(userId, toUnfollow);
    }
    public User updateUser(@NotNull long userId, @NotNull User modifiedUser) throws BadRequestException{
        if(modifiedUser == null){
            throw new BadRequestException("No Input Data");
        }
        User selectedUser = getUserById(userId);
        if(selectedUser != null){
            String newUsername = modifiedUser.getUsername();
            String newMail = modifiedUser.getEmail();
            String newFirstName = modifiedUser.getFirstName();
            String newLastName = modifiedUser.getLastName();

            selectedUser.setUsername(
                    (newUsername == null || newUsername.isEmpty())?
                            selectedUser.getUsername() :
                            newUsername
            );
            selectedUser.setEmail(
                    (newMail == null || newMail.isEmpty())?
                            selectedUser.getEmail() :
                            newMail
            );
            selectedUser.setFirstName(
                    (newFirstName == null || newFirstName.isEmpty())?
                            selectedUser.getFirstName() :
                            newFirstName
            );
            selectedUser.setLastName(
                    (newLastName == null || newLastName.isEmpty())?
                            selectedUser.getLastName() :
                            newLastName
            );
            return userRepository.updateUser(
                    selectedUser.getUserId(),
                    selectedUser.getUsername(),
                    selectedUser.getFirstName(),
                    selectedUser.getLastName(),
                    selectedUser.getEmail()
            );
        } else {
            return userRepository.saveToGraph(
                    modifiedUser.getUsername(),
                    modifiedUser.getEmail(),
                    modifiedUser.getFirstName(),
                    modifiedUser.getLastName(),
                    modifiedUser.getBucketId()
            );
        }


    }
    public void deleteUser(long userId){
        resourceService.deleteResourcesOfUser(userId);
        userRepository.deleteById(userId);
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }

}
