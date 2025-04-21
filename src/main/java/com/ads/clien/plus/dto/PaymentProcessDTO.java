package com.ads.clien.plus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessDTO {
    @NotBlank(message = "Nao pode ser vazio ou nulo")
    private String productKey;
    private BigDecimal discount;

    @NotNull(message = "dados do pagamento deve ser preenchido")
    @JsonProperty("userPaymentInfo")
    private UserPaymentInfoDTO userPaymentInfoDTO;

}
