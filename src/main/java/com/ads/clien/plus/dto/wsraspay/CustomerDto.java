package com.ads.clien.plus.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private String id;

    private String cpf;

    private String email;

    private String firstName;

    private String lastName;
}
