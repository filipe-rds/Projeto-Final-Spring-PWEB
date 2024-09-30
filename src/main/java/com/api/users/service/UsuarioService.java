package com.api.users.service;


import com.api.users.dto.UsuarioAtualizacaoDTO;
import com.api.users.dto.UsuarioCadastroDTO;
import com.api.users.dto.UsuarioListagemDTO;
import com.api.users.dto.UsuarioRetornadoDTO;
import com.api.users.entity.Usuario;
import com.api.users.exception.LoginFailedException;
import com.api.users.exception.UsuarioAlreadyExistsException;
import com.api.users.exception.UsuarioNotFoundException;
import com.api.users.exception.ValidationException;
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
            throw new UsuarioAlreadyExistsException("Usuário já cadastrado!");
        }
        repository.save(usuarioAdicionado);
        return new UsuarioRetornadoDTO(usuarioAdicionado.getId(), usuarioAdicionado.getNome(), usuarioAdicionado.getEmail());
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioAtualizacaoDTO usuarioDTO) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));

        // Lógica de validação
        if (usuarioDTO.nome() != null) {
            if (usuarioDTO.nome().isBlank() || usuarioDTO.nome().length() < 2 || usuarioDTO.nome().length() > 60) {
                throw new ValidationException("O nome deve ter entre 2 e 60 caracteres.");
            }
            usuarioExistente.setNome(usuarioDTO.nome());
        }

        if (usuarioDTO.email() != null) {
            if (!usuarioDTO.email().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                throw new ValidationException("O email deve ser válido e estar no formato correto.");
            }
            usuarioExistente.setEmail(usuarioDTO.email());
        }

        if (usuarioDTO.senha() != null) {
            if (!usuarioDTO.senha().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                throw new ValidationException("A senha deve conter pelo menos 8 caracteres, incluindo letras e números.");
            }
            usuarioExistente.setSenha(usuarioDTO.senha());
        }

        return repository.save(usuarioExistente);
    }

    public void remover(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));
        repository.delete(usuario);
    }


    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));
    }

    public List<UsuarioListagemDTO> listar() {
        return repository.findAll().stream().map(UsuarioListagemDTO::new).toList();
    }

    public UsuarioRetornadoDTO login(String email, String senha) {
        Usuario usuario = repository.findByEmail(email);
        if (!usuario.getSenha().equals(senha)) {
            throw new LoginFailedException("Login e senha incorretos!");
        }

        return new UsuarioRetornadoDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

}