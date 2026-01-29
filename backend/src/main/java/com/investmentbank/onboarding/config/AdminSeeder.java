package com.investmentbank.onboarding.config;

import com.investmentbank.onboarding.service.AdminAuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner seedDefaultAdmin(AdminAuthService adminAuthService) {
        return args -> {
            if (!adminAuthService.adminExists("admin")) {
                adminAuthService.createAdmin("admin", "admin123");
            }
        };
    }
}
