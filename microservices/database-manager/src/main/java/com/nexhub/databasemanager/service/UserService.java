package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.UserRepository;
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

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User u) throws Exception {
        /**
         * We want to have unity of mail
         * */
        boolean mailTaken = userRepository.isMailTaken(u.getMail());
        if (mailTaken){
            throw new Exception("The email : " +u.getMail()+" is already taken :/");
        }
        userRepository.save(u);
    }
    public void deleteUser(long userId){
        userRepository.deleteById(userId);
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }

}
