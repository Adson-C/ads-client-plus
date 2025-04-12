package com.ads.clien.plus.mapper.raspay;

import com.ads.clien.plus.dto.PaymentProcessDTO;
import com.ads.clien.plus.dto.wsraspay.OrderDto;

public class OrderMapper {
    public static OrderDto build(String customerId, PaymentProcessDTO paymentProcessDTO) {
        return OrderDto.builder()
                .customerId(customerId)
                .productAcronym(paymentProcessDTO.getProductKey())
                .discount(paymentProcessDTO.getDiscount())
                .build();
    }
}
