package com.api.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioLoginDTO(
        @NotBlank(message = "O email é obrigatório e não pode estar em branco.")
        @Email(message = "O email deve estar em um formato válido (exemplo: usuario@dominio.com).")
        String email,

        @NotBlank(message = "A senha é obrigatória e não pode estar em branco.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "A senha deve conter no mínimo 8 caracteres, incluindo letras e números."
        )
        String senha
) {
}
