package com.ads.clien.plus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private LocalDate dtPayment = LocalDate.now();

    private Long insytallments;
    @NotNull(message = "Nao pode ser vazio ou nulo")
    private long userId;
}
