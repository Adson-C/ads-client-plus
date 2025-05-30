package com.ads.clien.plus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTypeDTO {
    private Long id;
    @NotBlank(message = "Valor Não pode ser vazio ou nulo")
    private String name;
    @NotBlank(message = "Valor Não pode ser vazio ou nulo")
    private String description;
}
