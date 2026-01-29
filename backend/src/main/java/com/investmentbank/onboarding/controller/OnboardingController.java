package com.investmentbank.onboarding.controller;

import com.investmentbank.onboarding.model.ClientApplication;
import com.investmentbank.onboarding.service.OnboardingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @Autowired
    public OnboardingController(OnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @PostMapping("/")
    public ResponseEntity<ClientApplication> createApplication(@Valid @RequestBody ClientApplication application) {
        return ResponseEntity.ok(onboardingService.submitApplication(application));
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientApplication>> getAllApplications() {
        return ResponseEntity.ok(onboardingService.getAllApplications());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ClientApplication> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        
        String statusStr = statusUpdate.get("status");
        try {
            ClientApplication.Status status = ClientApplication.Status.valueOf(statusStr.toUpperCase());
            return onboardingService.updateStatus(id, status)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
