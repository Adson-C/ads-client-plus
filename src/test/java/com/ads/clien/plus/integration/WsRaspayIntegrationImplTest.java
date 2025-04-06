package com.ads.clien.plus.integration;

import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WsRaspayIntegrationImplTest {
    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void criarCustomerQuandoCorreto() {
        CustomerDto dto = new CustomerDto(null,"02371386030","teste@teste","Felipe","Alves");
        wsRaspayIntegration.createCustomer(dto);
    }
}
