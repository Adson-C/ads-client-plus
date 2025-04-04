package com.ads.clien.plus.mapper;

import com.ads.clien.plus.dto.UserDTO;
import com.ads.clien.plus.model.SubscriptionsType;
import com.ads.clien.plus.model.User;
import com.ads.clien.plus.model.UserType;

public class UserMapper {

    // Mapeando de DTO a Entidade
    public static User fromDtoToEntity(UserDTO dto, UserType userType, SubscriptionsType subscriptionsType) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .cpf(dto.getCpf())
                .dtSubscription(dto.getDtSubscription())
                .dtExpiration(dto.getDtExpiration())
                .userType(userType)
                .subscriptionsType(subscriptionsType)
                .build();
    }

}
