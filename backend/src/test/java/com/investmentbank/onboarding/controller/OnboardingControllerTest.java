package com.investmentbank.onboarding.controller;

import com.investmentbank.onboarding.model.ClientApplication;
import com.investmentbank.onboarding.service.OnboardingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OnboardingController.class)
class OnboardingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OnboardingService onboardingService;

    @Test
    void getAllApplicationsReturnsList() throws Exception {
        ClientApplication app = new ClientApplication();
        app.setId(1L);
        app.setCompanyName("Acme Corp");
        app.setCompanyNumber("12345678");
        app.setAnnualTurnover(new BigDecimal("1000"));
        app.setStatus(ClientApplication.Status.PENDING);

        when(onboardingService.getAllApplications()).thenReturn(List.of(app));

        mockMvc.perform(get("/api/applications/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].companyName").value("Acme Corp"));
    }

    @Test
    void createApplicationReturnsSavedEntity() throws Exception {
        ClientApplication app = new ClientApplication();
        app.setId(1L);
        app.setCompanyName("Acme Corp");
        app.setCompanyNumber("12345678");
        app.setAnnualTurnover(new BigDecimal("1000"));
        app.setStatus(ClientApplication.Status.PENDING);

        when(onboardingService.submitApplication(any(ClientApplication.class))).thenReturn(app);

        mockMvc.perform(post("/api/applications/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"companyName\":\"Acme Corp\",\"companyNumber\":\"12345678\",\"annualTurnover\":1000}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.companyNumber").value("12345678"));
    }

    @Test
    void updateStatusReturnsOkWhenFound() throws Exception {
        ClientApplication app = new ClientApplication();
        app.setId(1L);
        app.setStatus(ClientApplication.Status.APPROVED);

        when(onboardingService.updateStatus(Mockito.eq(1L), any(ClientApplication.Status.class)))
            .thenReturn(Optional.of(app));

        mockMvc.perform(patch("/api/applications/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"approved\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void updateStatusReturnsBadRequestForInvalidStatus() throws Exception {
        mockMvc.perform(patch("/api/applications/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\":\"invalid\"}"))
            .andExpect(status().isBadRequest());
    }
}
