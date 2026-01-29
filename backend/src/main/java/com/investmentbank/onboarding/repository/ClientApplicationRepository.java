package com.investmentbank.onboarding.repository;

import com.investmentbank.onboarding.model.ClientApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientApplicationRepository extends JpaRepository<ClientApplication, Long> {
	boolean existsByCompanyNumber(String companyNumber);
	boolean existsByCompanyNameIgnoreCase(String companyName);
}
