package com.api.users.service;


import com.api.users.dto.UsuarioAtualizacaoDTO;
import com.api.users.dto.UsuarioCadastroDTO;
import com.api.users.dto.UsuarioListagemDTO;
import com.api.users.dto.UsuarioRetornadoDTO;
import com.api.users.entity.Usuario;
import com.api.users.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public UsuarioRetornadoDTO adicionar(UsuarioCadastroDTO usuario) {
        Usuario usuarioAdicionado = new Usuario(usuario);
        if (repository.findByEmail(usuarioAdicionado.getEmail()) != null) {
            throw new RuntimeException("Usuário já cadastrado!");
        }
        repository.save(usuarioAdicionado);
        return new UsuarioRetornadoDTO(usuarioAdicionado.getId(), usuarioAdicionado.getNome(), usuarioAdicionado.getEmail());
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioAtualizacaoDTO usuarioDTO) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Validar nome
        if (usuarioDTO.nome() != null) {
            if (usuarioDTO.nome().isBlank() || usuarioDTO.nome().length() < 2 || usuarioDTO.nome().length() > 60) {
                throw new RuntimeException("O nome deve ter entre 2 e 60 caracteres.");
            }
            usuarioExistente.setNome(usuarioDTO.nome());
        }

        // Validar email
        if (usuarioDTO.email() != null) {
            if (!usuarioDTO.email().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new RuntimeException("O email deve ser válido e estar no formato correto(exemplo: usuario@dominio.com).");
            }
            usuarioExistente.setEmail(usuarioDTO.email());
        }

        // Validar senha
        if (usuarioDTO.senha() != null) {
            if (!usuarioDTO.senha().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                throw new RuntimeException("A senha deve conter pelo menos 8 caracteres, incluindo letras e números.");
            }
            usuarioExistente.setSenha(usuarioDTO.senha());
        }

        return repository.save(usuarioExistente);
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
