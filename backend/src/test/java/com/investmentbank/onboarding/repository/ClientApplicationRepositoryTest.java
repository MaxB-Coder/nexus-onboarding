package com.investmentbank.onboarding.repository;

import com.investmentbank.onboarding.model.ClientApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClientApplicationRepositoryTest {

    @Autowired
    private ClientApplicationRepository repository;

    @Test
    void existsByCompanyNumberAndNameIgnoreCase() {
        ClientApplication app = new ClientApplication();
        app.setCompanyName("Acme Corp");
        app.setCompanyNumber("12345678");
        app.setAnnualTurnover(new BigDecimal("1000"));
        app.setStatus(ClientApplication.Status.PENDING);
        app.setRiskRating("LOW");
        app.setSubmittedAt(LocalDateTime.now());

        repository.save(app);

        assertThat(repository.existsByCompanyNumber("12345678")).isTrue();
        assertThat(repository.existsByCompanyNameIgnoreCase("acme corp")).isTrue();
    }
}
