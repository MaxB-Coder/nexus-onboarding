package com.investmentbank.onboarding.service;

import com.investmentbank.onboarding.model.ClientApplication;
import com.investmentbank.onboarding.repository.ClientApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OnboardingServiceTest {

    private ClientApplicationRepository repository;
    private OnboardingService service;

    @BeforeEach
    void setup() {
        repository = mock(ClientApplicationRepository.class);
        service = new OnboardingService(repository);
    }

    @Test
    void submitApplicationRejectsDuplicateCompanyNumber() {
        ClientApplication app = new ClientApplication();
        app.setCompanyNumber("12345678");
        app.setCompanyName("Acme Corp");
        app.setAnnualTurnover(new BigDecimal("5000"));

        when(repository.existsByCompanyNumber("12345678")).thenReturn(true);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.submitApplication(app)
        );

        assertThat(ex.getStatusCode().value()).isEqualTo(409);
        verify(repository, never()).save(any());
    }

    @Test
    void submitApplicationRejectsDuplicateCompanyName() {
        ClientApplication app = new ClientApplication();
        app.setCompanyNumber("12345678");
        app.setCompanyName("Acme Corp");
        app.setAnnualTurnover(new BigDecimal("5000"));

        when(repository.existsByCompanyNumber("12345678")).thenReturn(false);
        when(repository.existsByCompanyNameIgnoreCase("Acme Corp")).thenReturn(true);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> service.submitApplication(app)
        );

        assertThat(ex.getStatusCode().value()).isEqualTo(409);
        verify(repository, never()).save(any());
    }

    @Test
    void submitApplicationSetsRiskAndStatus() {
        ClientApplication app = new ClientApplication();
        app.setCompanyNumber("12345678");
        app.setCompanyName("Acme Corp");
        app.setAnnualTurnover(new BigDecimal("20000000"));

        when(repository.existsByCompanyNumber("12345678")).thenReturn(false);
        when(repository.existsByCompanyNameIgnoreCase("Acme Corp")).thenReturn(false);
        when(repository.save(any(ClientApplication.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ClientApplication saved = service.submitApplication(app);

        assertThat(saved.getRiskRating()).isEqualTo("HIGH");
        assertThat(saved.getStatus()).isEqualTo(ClientApplication.Status.PENDING);
        assertThat(saved.getSubmittedAt()).isNotNull();

        ArgumentCaptor<ClientApplication> captor = ArgumentCaptor.forClass(ClientApplication.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getSubmittedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}
