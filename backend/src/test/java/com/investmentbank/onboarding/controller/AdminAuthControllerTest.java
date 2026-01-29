package com.investmentbank.onboarding.controller;

import com.investmentbank.onboarding.service.AdminAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminAuthController.class)
class AdminAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminAuthService adminAuthService;

    @Test
    void loginReturnsOkForValidCredentials() throws Exception {
        when(adminAuthService.authenticate("admin", "admin123")).thenReturn(true);

        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    void loginReturnsUnauthorizedForInvalidCredentials() throws Exception {
        when(adminAuthService.authenticate("admin", "bad"))
            .thenReturn(false);

        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"admin\",\"password\":\"bad\"}"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Invalid admin credentials"));
    }

    @Test
    void loginReturnsBadRequestWhenMissingFields() throws Exception {
        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }
}
