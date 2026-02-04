package com.arong.lab1.controller;

import com.arong.lab1.model.User;
import com.arong.lab1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // allow frontend calls
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) throws Exception {
        return authService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User request) throws Exception {
        return authService.login(request.getUsername(), request.getPassword());
    }
}
