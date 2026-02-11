package com.arong.g4.lab1.Controller;

import com.arong.g4.lab1.dto.LoginRequest;
import com.arong.g4.lab1.dto.LoginResponse;
import com.arong.g4.lab1.dto.UserDTO;
import com.arong.g4.lab1.model.User;
import com.arong.g4.lab1.Service.AuthService;
import com.arong.g4.lab1.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user);

            // Don't send password back
            UserDTO userDTO = new UserDTO(
                    registeredUser.getId(),
                    registeredUser.getUsername(),
                    registeredUser.getEmail()
            );

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("=== LOGIN REQUEST START ===");
        System.out.println("LoginRequest object: " + loginRequest);
        System.out.println("Username received: " + loginRequest.getUsername());
        System.out.println("Password received: " + (loginRequest.getPassword() != null ? "[PROVIDED - LENGTH: " + loginRequest.getPassword().length() + "]" : "[NULL]"));

        // Validate input
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            System.out.println("ERROR: Username is null or empty");
            Map<String, String> error = new HashMap<>();
            error.put("message", "Username/email is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            System.out.println("ERROR: Password is null or empty");
            Map<String, String> error = new HashMap<>();
            error.put("message", "Password is required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            System.out.println("Searching for user with username: " + loginRequest.getUsername());

            // Find user
            User user = authService.getUserByUsername(loginRequest.getUsername());

            if (user == null) {
                System.out.println("ERROR: User not found");
                Map<String, String> error = new HashMap<>();
                error.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            System.out.println("User found: " + user.getUsername());
            System.out.println("User ID: " + user.getId());
            System.out.println("Stored password hash: " + (user.getPassword() != null ? "[HASH EXISTS - LENGTH: " + user.getPassword().length() + "]" : "[NULL]"));

            // Check password
            System.out.println("Comparing passwords...");
            System.out.println("Raw password: " + (loginRequest.getPassword() != null ? "[PROVIDED]" : "[NULL]"));
            System.out.println("Encoded password from DB: " + (user.getPassword() != null ? "[EXISTS]" : "[NULL]"));

            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
            System.out.println("Password matches: " + passwordMatches);

            if (!passwordMatches) {
                System.out.println("ERROR: Password does not match");
                Map<String, String> error = new HashMap<>();
                error.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            System.out.println("Authentication successful! Generating token...");

            // Generate token
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());
            System.out.println("Token generated successfully");

            // Create response
            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );

            LoginResponse response = new LoginResponse(token, userDTO);

            System.out.println("=== LOGIN REQUEST SUCCESS ===");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("=== LOGIN REQUEST FAILED ===");
            System.out.println("Exception: " + e.getClass().getName());
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace();

            Map<String, String> error = new HashMap<>();
            error.put("message", "Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = authService.getUserByUsername(username);

            if (user == null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to get user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}