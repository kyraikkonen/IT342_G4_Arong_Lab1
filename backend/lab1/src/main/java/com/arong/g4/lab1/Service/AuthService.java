package com.arong.lab1.service;

import com.arong.lab1.model.User;
import com.arong.lab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        // For simplicity, storing password as plain text (not secure!)
        return userRepository.save(user);
    }

    public User login(String identifier, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByUsername(identifier);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(identifier);
        }

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            throw new Exception("Invalid credentials");
        }

        return userOpt.get();
    }
}
