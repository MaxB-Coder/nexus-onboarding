package com.investmentbank.onboarding.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "client_applications",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "company_name"),
        @UniqueConstraint(columnNames = "company_number")
    }
)
public class ClientApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name cannot be blank")
    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @NotBlank(message = "Company number is required")
    @Pattern(regexp = "^\\d{8}$", message = "Company number must be exactly 8 digits")
    @Column(name = "company_number", nullable = false, unique = true)
    private String companyNumber;

    @NotNull(message = "Annual turnover is required")
    @Positive(message = "Annual turnover must be positive")
    @Column(nullable = false)
    private BigDecimal annualTurnover;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String riskRating;

    private LocalDateTime submittedAt;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    // Default constructor for JPA
    public ClientApplication() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCompanyNumber() { return companyNumber; }
    public void setCompanyNumber(String companyNumber) { this.companyNumber = companyNumber; }

    public BigDecimal getAnnualTurnover() { return annualTurnover; }
    public void setAnnualTurnover(BigDecimal annualTurnover) { this.annualTurnover = annualTurnover; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getRiskRating() { return riskRating; }
    public void setRiskRating(String riskRating) { this.riskRating = riskRating; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}
