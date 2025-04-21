package com.ads.clien.plus.mapper;

import com.ads.clien.plus.dto.UserPaymentInfoDTO;
import com.ads.clien.plus.model.User;
import com.ads.clien.plus.model.UserPaymentInfo;

public class UserPaymentInfoMapper {

    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDTO dto, User user){
        return UserPaymentInfo.builder()
                .id(dto.getId())
                .cardNumber(dto.getCardNumber())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardSecurityCode(dto.getCardSecurityCode())
                .price(dto.getPrice())
                .dtPayment(dto.getDtPayment())
                .installments(dto.getInstallments())
                .user(user)
                .build();
    }
}
