package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.integration.MailIntegration;
import com.ads.clien.plus.model.jpa.UserCredentials;
import com.ads.clien.plus.model.redis.UserRecoveryCode;
import com.ads.clien.plus.repository.jpa.UserDetailsRepository;
import com.ads.clien.plus.repository.redis.UserRecoveryCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)


class UserDetailsServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Mock
    private MailIntegration mailIntegration;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private UserCredentials userCredentials;
    private static final String TEST_USERNAME = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_ENCODED_PASSWORD = "$2a$10$encodedPassword";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userDetailsService, "recoveryCodeTimeout", "30");
        
        userCredentials = new UserCredentials();
        userCredentials.setUsername(TEST_USERNAME);
        userCredentials.setPassword(TEST_ENCODED_PASSWORD);
    }


    @Test
    void loadUserByUsernameAndPass_ShouldThrowNotFoundException_WhenUserNotFound() {
        // Arrange
        when(userDetailsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundExceptionAds.class, () ->
                userDetailsService.loadUserByUsernameAndPass(TEST_USERNAME, TEST_PASSWORD));
    }

    @Test
    void loadUserByUsernameAndPass_ShouldThrowBadRequestException_WhenPasswordIsInvalid() {
        // Arrange
        when(userDetailsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(userCredentials));

        // Act & Assert
        assertThrows(BadReqequestExceptionAds.class, () ->
                userDetailsService.loadUserByUsernameAndPass(TEST_USERNAME, "wrong_password"));
    }

    @Test
    void sendRecovery_ShouldCreateNewRecoveryCode_WhenNoExistingCode() {
        // Arrange
        when(userRecoveryCodeRepository.findByEmail(TEST_USERNAME))
                .thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.of(userCredentials));

        // Act
        userDetailsService.sendRecovery(TEST_USERNAME);

        // Assert
        verify(userRecoveryCodeRepository).save(any(UserRecoveryCode.class));
        verify(mailIntegration).sendMail(eq(TEST_USERNAME), anyString(), anyString());
    }

    @Test
    void sendRecovery_ShouldUpdateExistingRecoveryCode_WhenCodeExists() {
        // Arrange
        UserRecoveryCode existingCode = new UserRecoveryCode();
        existingCode.setEmail(TEST_USERNAME);
        when(userRecoveryCodeRepository.findByEmail(TEST_USERNAME))
                .thenReturn(Optional.of(existingCode));

        // Act
        userDetailsService.sendRecovery(TEST_USERNAME);

        // Assert
        verify(userRecoveryCodeRepository).save(any(UserRecoveryCode.class));
        verify(mailIntegration).sendMail(eq(TEST_USERNAME), anyString(), anyString());
    }

    @Test
    void sendRecovery_ShouldThrowNotFoundException_WhenUserNotFound() {
        // Arrange
        when(userRecoveryCodeRepository.findByEmail(TEST_USERNAME))
                .thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(TEST_USERNAME))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundExceptionAds.class, () ->
                userDetailsService.sendRecovery(TEST_USERNAME));
    }
}