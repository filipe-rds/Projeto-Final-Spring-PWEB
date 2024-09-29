package com.api.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsuarioRecebidoDTO(
        @NotBlank(message = "O nome é obrigatório e não pode estar vazio ou em branco.")
        @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
        String nome,

        @NotBlank(message = "O email é obrigatório e não pode estar vazio ou em branco.")
        @Email(message = "O email deve ser válido e estar no formato correto (exemplo: usuario@dominio.com).")
        String email,

        @NotBlank(message = "A senha é obrigatória e não pode estar vazia ou em branco.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "A senha deve conter pelo menos 8 caracteres, incluindo letras e números."
        )
        String senha

) {
}
