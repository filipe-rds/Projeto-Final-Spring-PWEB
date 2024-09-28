package com.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UsuarioCadastroDTO(
        @NotBlank(message = "Nome não deve estar em branco!")
        String nome,
        @NotEmpty
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "A senha deve ter no mínimo 8 caracteres e incluir letras e números.")
        String senha

) {
}
