package com.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UsuarioRetornadoDTO(
        @NotEmpty(message = "Id não deve estar vazio")
        Long id,
        @Email
        String email
) {
}
