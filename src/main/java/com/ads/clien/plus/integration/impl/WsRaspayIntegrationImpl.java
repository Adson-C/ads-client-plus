package com.ads.clien.plus.integration.impl;

import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.dto.wsraspay.OrderDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;
import com.ads.clien.plus.integration.WsRaspayIntegration;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration  {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public WsRaspayIntegrationImpl() {
        restTemplate = new RestTemplate();
        headers = getHttpHeaders();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        try {

            HttpEntity<CustomerDto> request = new HttpEntity<>(customerDto, this.headers);
            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange("http://localhost:8081/ws-raspay/v1/customer", HttpMethod.POST, request, CustomerDto.class);
            return response.getBody();
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public Boolean processPayment(PaymentDto paymentDto) {
        return null;
    }
    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "rasmooplus:r@sm00";
        String base64 = new String( Base64.encodeBase64(credential.getBytes(), false));
        headers.add("Authorization","Basic "+base64);
        return headers;
    }
}
