package com.example.PersonalBlog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.PersonalBlog.exceptions.UserAlreadyExistsException;
import com.example.PersonalBlog.model.User;
import com.example.PersonalBlog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public void registerNewUser(User user) throws UserAlreadyExistsException{
        if(userExists(user.getEmail())){
            throw new UserAlreadyExistsException("User already exists");
        }
        userRepository.save(user);
    }

    private boolean userExists(String username){
        Optional<User> user = userRepository.findByEmail(username);
        return user.isPresent();
    }
}
