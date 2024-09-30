package com.api.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioAtualizacaoDTO(
        String nome,
        String email,
        String senha
) {
}
