package com.ads.clien.plus.integration;

import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.dto.wsraspay.OrderDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;

public interface WsRaspayIntegration {

    CustomerDto createCustomer(CustomerDto customerDto);
    OrderDto createOrder(OrderDto orderDto);

    Boolean processPayment(PaymentDto paymentDto);
}
