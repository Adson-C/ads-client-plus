package com.ads.clien.plus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionsTypeDTO {

    private Long id;
    @NotBlank(message = "Não pode ser vazio ou nulo")
    @Size(min = 5, max = 40, message = "Deve conter mais de 5 a menos de 40 caracteres")
    private String name;
    @Max(value = 12, message = "Apenas meses de acesso permitidos (1/12)")
    private Long accessMonths;
    @NotNull(message = "Não pode ser vazio ou nulo")
    private BigDecimal price;
    @NotBlank(message = "Não pode ser vazio ou nulo")
    @Size(min = 5, max = 15, message = "Deve conter mais de 5 a menos de 15 caracteres")
    private String productKey;
}
