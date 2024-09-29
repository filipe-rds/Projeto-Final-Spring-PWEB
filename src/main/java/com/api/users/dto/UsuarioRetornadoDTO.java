package com.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UsuarioRetornadoDTO(
        @NotEmpty(message = "O ID é obrigatório e não deve estar vazio.")
        Long id,

        @NotBlank(message = "O nome é obrigatório e não deve estar em branco.")
        String nome,

        @NotBlank(message = "O email é obrigatório e não deve estar em branco.")
        @Email(message = "O email deve estar em um formato válido (exemplo: usuario@dominio.com).")
        String email
) {
}
