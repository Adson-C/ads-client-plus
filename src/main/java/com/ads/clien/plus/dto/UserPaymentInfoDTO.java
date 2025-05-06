package com.ads.clien.plus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentInfoDTO {
    private Long id;
    @Size(min = 16, max = 16, message = "Deve conter 16 caracteres")
    private String cardNumber;
    @Min(value = 1, message = "Nao pode ser vazio ou nulo")
    @Max(value = 12, message = "Nao pode ser vazio ou nulo")
    private Long cardExpirationMonth;

    private Long cardExpirationYear;
    @Size(min = 3, max = 3, message = "Deve conter 3 caracteres")
    private String cardSecurityCode;

    private BigDecimal price;
    @Builder.Default
    private LocalDate dtPayment = LocalDate.now();

    private Long installments;
    @NotNull(message = "Nao pode ser vazio ou nulo")
    private long userId;
}
