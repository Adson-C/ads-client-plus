package com.ads.clien.plus.mapper;

import com.ads.clien.plus.dto.UserTypeDTO;
import com.ads.clien.plus.model.UserType;

public class UserTypeMapper {

    // Mapeando de DTO a Entidade
    public static UserType fromDtoToEntity(UserTypeDTO dto) {
        return UserType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

}
