package com.ads.clien.plus.mapper.raspay;

import com.ads.clien.plus.dto.wsraspay.CustomerDto;
import com.ads.clien.plus.model.jpa.User;

public class CustomerMapper {

    public static CustomerDto build(User user) {
        // Usar nome completo
        var fullName = user.getName().split(" ");
        var firstName = fullName[0];
        var lastName = fullName.length > 1 ? fullName[fullName.length - 1] : "";

        return CustomerDto.builder()
                .email(user.getEmail())
                .cpf(user.getCpf())
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
