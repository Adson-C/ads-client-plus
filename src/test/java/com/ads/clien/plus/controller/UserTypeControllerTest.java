package com.ads.clien.plus.controller;

import com.ads.clien.plus.model.jpa.UserType;
import com.ads.clien.plus.service.UserTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(UserTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTypeService userTypeService;

    @Test
    void given_findAll_then_returnUserType() throws Exception {
        List<UserType> userTypeList = List.of(
                new UserType(1L, "Professor", "Professor da UFMG"),
                new UserType(2L, "Aluno", "Aluno da UFMG")
        );
        when(userTypeService.findAll()).thenReturn(userTypeList);
        mockMvc.perform(MockMvcRequestBuilders.get("/usertype"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
}