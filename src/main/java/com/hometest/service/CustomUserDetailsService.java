package com.hometest.service;

import com.hometest.controllers.data.Admin;
import com.hometest.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * CustomUserDetailsService implements Spring Security's UserDetailsService interface.
 * It provides a method to load user details from the database based on the username.
 * This service is used for authentication purposes.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    /**
     * Loads user details by username.
     *
     * @param name The username of the user to load.
     * @return UserDetails object containing the user's details.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // Attempt to find the user by name in the repository
        Admin user = (Admin) userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + name));

        // Create and return a UserDetails object using the found user's details
        return new org.springframework.security.core.userdetails.User(
                user.getName(), // Username
                user.getPassword(), // Password
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())) // Authorities (roles)
        );
    }
}