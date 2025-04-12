package com.ads.clien.plus.mapper.raspay;

import com.ads.clien.plus.dto.UserPaymentInfoDTO;
import com.ads.clien.plus.dto.wsraspay.CreditCardDto;

public class CreditCardMapper {
    public static CreditCardDto build(UserPaymentInfoDTO dto, String documentNumber) {
        return CreditCardDto.builder()
                .documentNumber(documentNumber)
                .cvv(Long.parseLong(dto.getCardSecurityCode()))
                .number(dto.getCardNumber())
                .month(dto.getCardExpirationMonth())
                .year(dto.getCardExpirationYear())
                .installments(dto.getInstallments())
                .build();
    }
}
