package com.ads.clien.plus.service.impl;

import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.exception.BadReqequestExceptionAds;
import com.ads.clien.plus.exception.NotFoundExceptionAds;
import com.ads.clien.plus.model.jpa.User;
import com.ads.clien.plus.model.jpa.UserType;
import com.ads.clien.plus.repository.jpa.UserRepository;
import com.ads.clien.plus.repository.jpa.UserTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock   
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserDTO userDTO;

    @BeforeEach
    public void loaddUser() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("teste@teste.com");
        userDTO.setCpf("12345678901");
        userDTO.setUserTypeId(1L);

    }

    @Test
    void criar_quando_IdNao_for_Nullo_retorna_Usuario_Criado() {


        UserType userType = getUserType();

        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        userDTO.setId(null);

        User user = getUser(userType);
        when(userRepository.save(user)).thenReturn(user);

        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        Assertions.assertEquals(userServiceImpl.create(userDTO), user);

        verify(userRepository).save(user);
        verify(userTypeRepository).findById(1L);
        
    }
    @Test
    void criar_quando_Id_Nao_For_Nullo_retorna_BadReqequestExceptionAds() {   
       
        
        Assertions.assertThrows(BadReqequestExceptionAds.class, () -> {
            userServiceImpl.create(userDTO);
            Assertions.assertEquals(BadReqequestExceptionAds.class, userServiceImpl.create(userDTO));
        });
        verify(userTypeRepository, times(0)).findById(any());
         verify(userRepository, times(0)).save(any());
    }
    @Test
    void criar_quando_Id_Nao_Nullo_retorna_NotFoundExceptionAds() {
        
        userDTO.setId(null);

        when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundExceptionAds.class, () -> {
            userServiceImpl.create(userDTO);
            
        });
        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void given_uploadPhoto_when_thereIsUserAndFileAndIsPNGorJPEG_then_updatePhotoAndReturnUser() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/static/bor.png");
        MockMultipartFile file = new MockMultipartFile("file", "bor.png", MediaType.MULTIPART_FORM_DATA_VALUE, fileInputStream);

        UserType userType = getUserType();
        User user = getUser(userType);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        User userReturned = userServiceImpl.uploadPhoto(2L, file);
        assertNotNull(userReturned);
        assertNotNull(userReturned.getPhoto());
        assertEquals("bor.png", userReturned.getPhotoName());

        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void given_uploadPhoto_when_thereIsUserAndFileAndIsNotPNGorJPEG_then_throwBadRequestException() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/static/bor.png");
        MockMultipartFile file = new MockMultipartFile("file", "bor.txt", MediaType.MULTIPART_FORM_DATA_VALUE, fileInputStream);

        assertThrows(BadReqequestExceptionAds.class, () -> userServiceImpl.uploadPhoto(2L, file));
        verify(userRepository, times(0)).findById(any());
    }
    @Test
    void given_downloadPhoto_when_thereIsUserAndPhoto_then_returnByteArray() throws Exception {

        UserType userType = getUserType();
        User user = getUser(userType);
        user.setPhoto(new byte[0]);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        assertNotNull(userServiceImpl.downloadPhoto(2L));
        verify(userRepository, times(1)).findById(2L);
    }
    @Test
    void given_downloadPhoto_when_thereIsUserAndThereIsNoPhoto_then_throwBadRequestException() throws Exception {

        UserType userType = getUserType();
        User user = getUser(userType);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        assertThrows(BadReqequestExceptionAds.class, ()->userServiceImpl.downloadPhoto(2L));
        verify(userRepository, times(1)).findById(2L);
    }
    private static UserType getUserType() {
        return new UserType(1L, "Professor", "Professor da Universidade Federal de Minas Gerais");
    }
    private User getUser(UserType userType) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setCpf(userDTO.getCpf());
        user.setDtSubscription(userDTO.getDtSubscription());
        user.setDtExpiration(userDTO.getDtExpiration());
        user.setUserType(userType);
        return user;
    }
}
