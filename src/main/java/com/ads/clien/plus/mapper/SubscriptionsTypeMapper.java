package com.ads.clien.plus.mapper;

import com.ads.clien.plus.dto.SubscriptionsTypeDTO;
import com.ads.clien.plus.model.SubscriptionsType;

public class SubscriptionsTypeMapper {

    // Mapeando de DTO a Entidade
    public static SubscriptionsType fromDtoToEntity(SubscriptionsTypeDTO dto) {
        return SubscriptionsType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .accessMonths(dto.getAccessMonths())
                .price(dto.getPrice())
                .productKey(dto.getProductKey())
                .build();
    }
}
