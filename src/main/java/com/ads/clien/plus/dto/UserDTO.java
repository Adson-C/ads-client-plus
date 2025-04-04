package com.ads.clien.plus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotBlank(message = "Valor Não pode ser vazio ou nulo")
    @Size(min = 6, message = "Valor deve conter pelo menos 6 caracteres")
    private String name;
    @Email(message = "Email inválido")
    private String email;
    @Size(min = 11, message = "Telefone deve conter pelo menos 11 digitos")
    private String phone;
    @CPF(message = "CPF inválido")
    private String cpf;
    private LocalDate dtSubscription = LocalDate.now();
    private LocalDate dtExpiration = LocalDate.now();
    @NotNull
    private long userTypeId;
    private long subscriptionsTypeId;

}
