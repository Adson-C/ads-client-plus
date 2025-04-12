package com.ads.clien.plus.integration.impl;

import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.dto.wsraspay.OrderDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;
import com.ads.clien.plus.exception.IntegrationException;
import com.ads.clien.plus.integration.WsRaspayIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;


@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration  {

    @Value("${webservices.raspay.host}")
    private String raspayHost;

    @Value("${webservices.raspay.v1.customer}")
    private String customerUrl;

    @Value("${webservices.raspay.v1.order}")
    private String orderUrl;

    @Value("${webservices.raspay.v1.payment}")
    private String paymentUrl;

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public WsRaspayIntegrationImpl() {
        restTemplate = new RestTemplate();
        headers = getHttpHeaders();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {

            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange(raspayHost+customerUrl, HttpMethod.POST, request, CustomerDto.class);
            return response.getBody();
        }catch (IntegrationException e) {
            throw new IntegrationException("Erro ao acessar o WebService Raspay: "+e.getMessage());
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        try {

            HttpEntity<OrderDto> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<OrderDto> response =
                    restTemplate.exchange(raspayHost+orderUrl, HttpMethod.POST, request, OrderDto.class);
            return response.getBody();
        }catch (IntegrationException e) {
            throw new IntegrationException("Erro ao acessar o WebService Raspay: "+e.getMessage());
        }
    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
        try {

            HttpEntity<PaymentDto> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<Boolean> response =
                    restTemplate.exchange(raspayHost+paymentUrl, HttpMethod.POST, request, Boolean.class);
            return response.getBody();
        }catch (IntegrationException e) {
            throw new IntegrationException("Erro ao acessar o WebService Raspay: "+e.getMessage());
        }
    }
//    private HttpHeaders getHttpHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        String credential = "rasmooplus:r@sm00";
//        String base64 = new String(Base64.encodeBase64(credential.getBytes(), false));
//        headers.add("Authorization","Basic "+base64);
//        return headers;
//    }
private HttpHeaders getHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    String credential = "rasmooplus:r@sm00";

    // Codifica em Base64
    String base64 = Base64.getEncoder().encodeToString(credential.getBytes());

    headers.add("Authorization", "Basic " + base64);
    return headers;
}
}
