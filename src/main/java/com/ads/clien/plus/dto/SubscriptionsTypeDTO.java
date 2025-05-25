package com.ads.clien.plus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionsTypeDTO {

    private Long id;
    @Size(min = 5, max = 40, message = "Deve conter mais de 5 a menos de 40 caracteres")
    private String name;
    @Max(value = 12, message = "Apenas meses de acesso permitidos (1/12)")
    private Long accessMonths;
    @NotNull(message = "Não pode ser vazio ou nulo")
    private BigDecimal price;
    @Size(min = 5, max = 15, message = "Deve conter mais de 5 a menos de 15 caracteres")
    private String productKey;
}
