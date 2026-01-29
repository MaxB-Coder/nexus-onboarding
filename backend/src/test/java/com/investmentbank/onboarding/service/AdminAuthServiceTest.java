package com.investmentbank.onboarding.service;

import com.investmentbank.onboarding.model.AdminUser;
import com.investmentbank.onboarding.repository.AdminUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminAuthServiceTest {

    private AdminUserRepository repository;
    private AdminAuthService service;

    @BeforeEach
    void setup() {
        repository = mock(AdminUserRepository.class);
        service = new AdminAuthService(repository);
    }

    @Test
    void authenticateReturnsTrueForValidPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("secret123");

        when(repository.findByUsername("admin")).thenReturn(Optional.of(new AdminUser("admin", hash)));

        assertThat(service.authenticate("admin", "secret123")).isTrue();
    }

    @Test
    void authenticateReturnsFalseForInvalidPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("secret123");

        when(repository.findByUsername("admin")).thenReturn(Optional.of(new AdminUser("admin", hash)));

        assertThat(service.authenticate("admin", "wrong")).isFalse();
    }

    @Test
    void createAdminHashesPassword() {
        when(repository.save(any(AdminUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AdminUser created = service.createAdmin("admin", "secret123");

        assertThat(created.getUsername()).isEqualTo("admin");
        assertThat(created.getPasswordHash()).isNotEqualTo("secret123");
    }

    @Test
    void adminExistsDelegatesToRepository() {
        when(repository.existsByUsername("admin")).thenReturn(true);
        assertThat(service.adminExists("admin")).isTrue();
    }
}
