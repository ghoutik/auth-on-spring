package com.test.loopy.service;

import com.test.loopy.dto.RegisterRequest;
import com.test.loopy.model.User;
import com.test.loopy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(hashPassword(password)))
                .orElse(false);
    }

    public void register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setPassword(hashPassword(req.password));

        userRepository.save(user);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
