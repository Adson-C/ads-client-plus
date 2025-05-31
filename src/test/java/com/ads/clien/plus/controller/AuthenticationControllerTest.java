package com.ads.clien.plus.controller;

import com.ads.clien.plus.dto.LoginDTO;
import com.ads.clien.plus.dto.UserDetailsDTO;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.model.redis.UserRecoveryCode;
import com.ads.clien.plus.service.AuthenticationService;
import com.ads.clien.plus.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@AutoConfigureDataJpa
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenInvalidLoginDTO_whenAuth_thenReturnsNotFound() throws Exception {
        LoginDTO dto = new LoginDTO("nonexistent@example.com", "password");

        Mockito.when(authenticationService.auth(Mockito.any(LoginDTO.class)))
                .thenThrow(new NotFoundExceptionAds("Usuário não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidEmail_whenSendRecoveryCode_thenReturnsNoContent() throws Exception {
        UserRecoveryCode recoveryCode = new UserRecoveryCode();

        Mockito.doNothing().when(userDetailsService).sendRecovery(Mockito.anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recoveryCode)))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenValidParams_whenRecoveryCodeIsValid_thenReturnsTrue() throws Exception {
        Mockito.when(userDetailsService.recoveryCodeIsValid("123456", "user@example.com"))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/recovery-code/")
                        .param("recoveryCode", "123456")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void givenValidDTO_whenUpdatePasswordByRecoveryCode_thenReturnsNoContent() throws Exception {
        UserDetailsDTO dto = new UserDetailsDTO("user@example.com", "newPassword", "123456");

        Mockito.doNothing().when(userDetailsService).updatePasswordByRecoveryCode(Mockito.any(UserDetailsDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

}