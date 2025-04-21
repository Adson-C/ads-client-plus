package com.ads.clien.plus.integration;

import com.ads.clien.plus.dto.wsraspay.CreditCardDto;
import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.dto.wsraspay.OrderDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class WsRaspayIntegrationImplTest {
    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void criarCustomerQuandoCorreto() {
        CustomerDto dto = new CustomerDto(null,"36332236817","adson@yahoo.com","Adson","Sá");
        wsRaspayIntegration.createCustomer(dto);
    }
    @Test
    void criarOrderQuandoCorreto() {
        OrderDto dto = new OrderDto(null, "67f72b278abd71546658f115", BigDecimal.ZERO, "PERPETUAL22");
        wsRaspayIntegration.createOrder(dto);
    }

    @Test
    void processPaymentQuandoCorreto() {
        CreditCardDto creditCardDto = new CreditCardDto(123L, "02371386034", 0L, 012L, "1234567891234567", 2025L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto, "67f72b278abd71546658f115", "67f86489004b131d58ad95e3");
        wsRaspayIntegration.processPayment(paymentDto);
    }
}
