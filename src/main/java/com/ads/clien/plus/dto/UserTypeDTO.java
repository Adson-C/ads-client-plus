package com.ads.clien.plus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
