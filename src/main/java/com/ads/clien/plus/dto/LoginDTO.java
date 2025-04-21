package com.ads.clien.plus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "atributo não pode ser vazio ou nulo")
    private String username;
    @NotBlank(message = "atributo não pode ser vazio ou nulo")
    private String password;
}
