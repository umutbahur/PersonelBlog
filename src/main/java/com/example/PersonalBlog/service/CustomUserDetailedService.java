package com.example.PersonalBlog.service;

import org.springframework.stereotype.Service;
import com.example.PersonalBlog.repository.UserRepository;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailedService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        com.example.PersonalBlog.model.User user = userRepository.findByEmail(username).orElseThrow(() -> 
            new UsernameNotFoundException("email not found."));
        return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));
    }
}
