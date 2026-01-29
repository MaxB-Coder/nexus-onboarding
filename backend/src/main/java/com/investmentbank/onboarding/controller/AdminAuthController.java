package com.investmentbank.onboarding.controller;

import com.investmentbank.onboarding.service.AdminAuthService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @Autowired
    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AdminLoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username and password are required"));
        }

        boolean authenticated = adminAuthService.authenticate(request.username(), request.password());
        if (!authenticated) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid admin credentials"));
        }

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    public record AdminLoginRequest(
        @NotBlank String username,
        @NotBlank String password
    ) {}
}
