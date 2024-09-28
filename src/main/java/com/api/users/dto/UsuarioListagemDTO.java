package com.api.users.dto;

import com.api.users.entity.Usuario;

public record UsuarioListagemDTO(Long id,String nome, String email) {
    public UsuarioListagemDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
