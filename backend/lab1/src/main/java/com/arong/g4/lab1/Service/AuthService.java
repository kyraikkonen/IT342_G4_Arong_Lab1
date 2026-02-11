package com.arong.g4.lab1.Service;

import com.arong.g4.lab1.model.User;
import com.arong.g4.lab1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        // First try to find by username
        User user = userRepository.findByUsername(username).orElse(null);

        // If not found, try to find by email
        if (user == null) {
            user = userRepository.findByEmail(username).orElse(null);
        }

        return user;
    }
}