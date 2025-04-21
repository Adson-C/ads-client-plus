package com.ads.clien.plus.mapper.raspay;

import com.ads.clien.plus.dto.wsraspay.CreditCardDto;
import com.ads.clien.plus.dto.wsraspay.PaymentDto;

public class PaymentMapper {
    public static PaymentDto build(String customerId, String orderId, CreditCardDto dto) {
        return PaymentDto.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(dto)
                .build();
    }
}
