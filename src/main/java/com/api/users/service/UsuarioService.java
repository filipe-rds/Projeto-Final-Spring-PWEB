package com.api.users.service;


import com.api.users.dto.UsuarioRecebidoDTO;
import com.api.users.dto.UsuarioListagemDTO;
import com.api.users.dto.UsuarioRetornadoDTO;
import com.api.users.entity.Usuario;
import com.api.users.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public UsuarioRetornadoDTO adicionar(UsuarioRecebidoDTO usuario) {
        Usuario usuarioAdicionado = new Usuario(usuario);
        if (repository.findByEmail(usuarioAdicionado.getEmail()) != null) {
            throw new RuntimeException("Usuário já cadastrado!");
        }
        repository.save(usuarioAdicionado);
        return new UsuarioRetornadoDTO(usuarioAdicionado.getId(), usuarioAdicionado.getNome(), usuarioAdicionado.getEmail());
    }

    @Transactional
    public Usuario atualizar(Long id, @Valid UsuarioRecebidoDTO usuarioDTO) {
        // Buscar o usuário existente no banco de dados
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Atualizar os campos enviados no DTO
        if (!usuarioDTO.nome().isBlank()) {
            usuario.setNome(usuarioDTO.nome());
        }

        if (!usuarioDTO.email().isBlank()) {
            usuario.setEmail(usuarioDTO.email());
        }

        if (!usuarioDTO.senha().isBlank()) {
            usuario.setSenha(usuarioDTO.senha());
        }

        // Salvar e retornar a entidade atualizada
        return repository.save(usuario);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<UsuarioListagemDTO> listar() {
        return repository.findAll().stream().map(UsuarioListagemDTO::new).toList();
    }

    public UsuarioRetornadoDTO login(String email, String senha) {
        Usuario usuario = buscarPorEmail(email);
        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Login e senha incorretos!");
        }

        return new UsuarioRetornadoDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

}
