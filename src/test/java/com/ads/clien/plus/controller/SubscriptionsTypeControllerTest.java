package com.ads.clien.plus.controller;

import com.ads.clien.plus.dto.SubscriptionsTypeDTO;
import com.ads.clien.plus.model.jpa.SubscriptionsType;
import com.ads.clien.plus.service.SubscriptionTypeService;
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
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionsTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class SubscriptionsTypeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionTypeService subscriptionTypeService;

    @Test
    void given_findAll_then_returnSubscriptionType() throws Exception {

        mockMvc.perform(get("/subscriptions-type"))
                .andExpect(status().isOk());
    }
    
    @Test
    void given_findById_whenGetId_then_returnOneSubscriptionType() throws Exception {
        SubscriptionsType subscriptionsType = new SubscriptionsType(2L, "VITALICIO",
                null, BigDecimal.valueOf(997), "FOREVER2022");
        when(subscriptionTypeService.findById(2L)).thenReturn(subscriptionsType);

        mockMvc.perform(get("/subscriptions-type/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)));
    }
    @Test
    void given_delete_whenGetId2_then_NoReturnAndNoContent() throws Exception {

        mockMvc.perform(delete("/subscriptions-type/{id}", 2))
                .andExpect(status().isNoContent());

        verify(subscriptionTypeService, times(1)).delete(2L);
    }
//    given_update_whenDtoIsOk_then_returnSubscriptionTypeUpdate
    @Test
    void given_create_whenDtoIsOk_then_returnSubscriptionTypeCreate() throws Exception {
        SubscriptionsType subscriptionsType = new SubscriptionsType(2L, "VITALICIO",
                null, BigDecimal.valueOf(997), "FOREVER2022");
        
        SubscriptionsTypeDTO dto = new SubscriptionsTypeDTO(null, "VITALICIO",
                null, BigDecimal.valueOf(997), "FOREVER2022");

        when(subscriptionTypeService.create(dto)).thenReturn(subscriptionsType);

        mockMvc.perform(post("/subscriptions-type")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)));
    }

    @Test
    void given_create_whenDtoIsMissing_then_returnBadRequest() throws Exception {

        SubscriptionsTypeDTO dto = new SubscriptionsTypeDTO(null, "",
                13L, null, "22");

        mockMvc.perform(post("/subscriptions-type")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                 .andExpect(jsonPath("$.message", is("[price=Não pode ser vazio ou nulo, accessMonths=Apenas meses de acesso permitidos (1/12), name=Deve conter mais de 5 a menos de 40 caracteres, productKey=Deve conter mais de 5 a menos de 15 caracteres]")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode", is(400)));

        verify(subscriptionTypeService, times(0)).create(any());
    }
    @Test
    void given_update_whenDtoIsOk_then_returnSubscriptionTypeUpdate() throws Exception {
        SubscriptionsType subscriptionsType = new SubscriptionsType(2L, "VITALICIO",
                null, BigDecimal.valueOf(997), "FOREVER2022");

        SubscriptionsTypeDTO dto = new SubscriptionsTypeDTO(2L, "VITALICIO",
                null, BigDecimal.valueOf(997), "FOREVER2022");

        when(subscriptionTypeService.update(2L, dto)).thenReturn(subscriptionsType);

        mockMvc.perform(put("/subscriptions-type/2")
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));
    }
}