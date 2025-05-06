package com.ads.clien.plus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Builder.Default
    private LocalDate dtSubscription = LocalDate.now();
    @Builder.Default
    private LocalDate dtExpiration = LocalDate.now();
    @NotNull
    private long userTypeId;
    private long subscriptionsTypeId;

}
