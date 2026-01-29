package com.investmentbank.onboarding.service;

import com.investmentbank.onboarding.model.AdminUser;
import com.investmentbank.onboarding.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AdminAuthService {

    private final AdminUserRepository adminUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminAuthService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean authenticate(String username, String password) {
        return adminUserRepository.findByUsername(username)
            .map(user -> passwordEncoder.matches(password, user.getPasswordHash()))
            .orElse(false);
    }

    public AdminUser createAdmin(String username, String password) {
        String hash = passwordEncoder.encode(password);
        return adminUserRepository.save(new AdminUser(username, hash));
    }

    public boolean adminExists(String username) {
        return adminUserRepository.existsByUsername(username);
    }
}
