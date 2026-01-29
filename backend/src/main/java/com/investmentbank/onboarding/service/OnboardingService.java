package com.investmentbank.onboarding.service;

import com.investmentbank.onboarding.model.ClientApplication;
import com.investmentbank.onboarding.repository.ClientApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OnboardingService {

    private final ClientApplicationRepository repository;
    private static final BigDecimal HIGH_RISK_THRESHOLD = new BigDecimal("10000000");

    @Autowired
    public OnboardingService(ClientApplicationRepository repository) {
        this.repository = repository;
    }

    public ClientApplication submitApplication(ClientApplication app) {
        if (repository.existsByCompanyNumber(app.getCompanyNumber())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Company number already exists"
            );
        }

        if (repository.existsByCompanyNameIgnoreCase(app.getCompanyName())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Company name already exists"
            );
        }

        // Business Logic: Calculate Risk Rating
        if (app.getAnnualTurnover().compareTo(HIGH_RISK_THRESHOLD) > 0) {
            app.setRiskRating("HIGH");
        } else {
            app.setRiskRating("LOW");
        }

        // Set initial status and submission timestamp
        app.setStatus(ClientApplication.Status.PENDING);
        app.setSubmittedAt(LocalDateTime.now());

        return repository.save(app);
    }

    public List<ClientApplication> getAllApplications() {
        return repository.findAll();
    }

    public Optional<ClientApplication> updateStatus(@NonNull Long id, ClientApplication.Status status) {
        return repository.findById(id).map(app -> {
            app.setStatus(status);
            return repository.save(app);
        });
    }
}
