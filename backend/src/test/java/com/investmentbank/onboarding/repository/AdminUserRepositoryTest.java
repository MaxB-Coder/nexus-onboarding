package com.investmentbank.onboarding.repository;

import com.investmentbank.onboarding.model.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AdminUserRepositoryTest {

    @Autowired
    private AdminUserRepository repository;

    @Test
    void saveAndFindByUsername() {
        AdminUser user = new AdminUser("admin", "hash");
        repository.save(user);

        assertThat(repository.findByUsername("admin")).isPresent();
        assertThat(repository.existsByUsername("admin")).isTrue();
    }
}
