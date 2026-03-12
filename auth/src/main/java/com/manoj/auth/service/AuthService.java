package com.manoj.auth.service;

import com.manoj.auth.dto.AuthResponse;
import com.manoj.auth.dto.LoginRequest;
import com.manoj.auth.dto.RegisterRequest;
import com.manoj.auth.model.User;
import com.manoj.auth.repository.UserRepository;
import com.manoj.auth.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(RegisterRequest registerRequest) {
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        else {
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            User savedUser = userRepository.save(user);
            // Call User Service to create profile
            Map<String, Object> profile = new HashMap<>();
            profile.put("userId", savedUser.getId());
            profile.put("username", savedUser.getUsername());
            profile.put("email", savedUser.getEmail());
            profile.put("bio", "");
            profile.put("avatarUrl", "");

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(
                    "http://localhost:8082/internal/users",
                    profile,
                    String.class
            );
            return savedUser;
        }
    }
    public AuthResponse login(LoginRequest request){

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}
