package com.ads.clien.plus.service;

import com.ads.clien.plus.model.jpa.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceTest {

    @Mock
    private UserTypeService userTypeService;

    /*
     * given_metodo_when_cenario_then_retornoEsperado
     * 
    */

    @InjectMocks
    private UserTypeServiceTest test;

    @Test
    void retona_tOdosDados_DoBancoDeDados() {
        List<UserType> userTypes = new ArrayList<>();
        userTypes.add(new UserType(1L, "Professor", "Professor da Universidade Federal de Minas Gerais"));
        userTypes.add(new UserType(2L, "Aluno", "Aluno da Universidade Federal de Minas Gerais"));

        when(userTypeService.findAll()).thenReturn(userTypes);

        List<UserType> result = userTypeService.findAll();

        assertEquals(userTypes, result);
        System.out.println(result);
    }
}
