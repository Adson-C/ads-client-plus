package com.ads.clien.plus.integration.impl;

import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.exception.IntegrationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationImplTest {

    private static HttpHeaders headers;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegrationImpl;


    @BeforeAll
    public static void loadHeaders() {
        headers = getHttpHeaders();
    }

    @Test
    void given_createCustomer_when_apiresponse201Created_then_returnCustomerDto() {
        ReflectionTestUtils.setField(wsRaspayIntegrationImpl, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegrationImpl, "customerUrl", "/customers");
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCpf("12345678901");
        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, this.headers);
        when(restTemplate.exchange("http://localhost:8080/customers", HttpMethod.POST, request, CustomerDto.class))
        .thenReturn(ResponseEntity.of(Optional.of(customerDto)));
        assertEquals(customerDto, wsRaspayIntegrationImpl.createCustomer(customerDto));

        verify(restTemplate, times(1)).exchange("http://localhost:8080/customers", HttpMethod.POST, request, CustomerDto.class);
    }
    @Test
    void given_createCustomer_when_apiresponse400BadRequest_then_returnNull() {
        ReflectionTestUtils.setField(wsRaspayIntegrationImpl, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegrationImpl, "customerUrl", "/customers");
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCpf("12345678901");
        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, this.headers);
        when(restTemplate.exchange("http://localhost:8080/customers", HttpMethod.POST, request, CustomerDto.class))
                .thenReturn(ResponseEntity.badRequest().build());
        assertNull(wsRaspayIntegrationImpl.createCustomer(customerDto));

        verify(restTemplate, times(1)).exchange("http://localhost:8080/customers", HttpMethod.POST, request, CustomerDto.class);
    }

    @Test
    void given_createCustomer_when_apiresponse4Gethrows_then_returnthrowIntegrationException() {
        ReflectionTestUtils.setField(wsRaspayIntegrationImpl, "raspayHost", "http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegrationImpl, "customerUrl", "/customers");
        // Given
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCpf("12345678901");
        HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, this.headers);
        when(restTemplate.exchange("http://localhost:8080/customers", HttpMethod.POST, request, CustomerDto.class))
                .thenThrow(new IntegrationException("Erro ao acessar o WebService Raspay: "));
        assertThrows(IntegrationException.class, () -> wsRaspayIntegrationImpl.createCustomer(customerDto));

        verify(restTemplate, times(1)).exchange("http://localhost:8080/customers", HttpMethod.POST, request, CustomerDto.class);
    }
    

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "rasmooplus:r@sm00";

        // Codifica em Base64
        String base64 = Base64.getEncoder().encodeToString(credential.getBytes());

        headers.add("Authorization", "Basic " + base64);
        return headers;
    }


}