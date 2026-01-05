package com.test.loopy.controllers;

import com.test.loopy.dto.LoginRequest;
import com.test.loopy.dto.RegisterRequest;
import com.test.loopy.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest request) {
        return authService.login(request.username, request.password);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("OK");
    }
}
