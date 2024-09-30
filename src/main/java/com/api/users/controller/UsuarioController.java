package com.api.users.controller;

import com.api.users.dto.*;
import com.api.users.entity.Usuario;
import com.api.users.exception.LoginFailedException;
import com.api.users.exception.UsuarioAlreadyExistsException;
import com.api.users.exception.UsuarioNotFoundException;
import com.api.users.exception.ValidationException;
import com.api.users.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Adicionar um novo usuário
    @PostMapping
    public ResponseEntity<UsuarioRetornadoDTO> adicionar(@RequestBody @Valid UsuarioCadastroDTO usuarioDTO) {
        try {
            UsuarioRetornadoDTO usuarioAdicionado = service.adicionar(usuarioDTO);
            return new ResponseEntity<>(usuarioAdicionado, HttpStatus.CREATED);
        } catch (UsuarioAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Atualizar um usuário existente
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioRetornadoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioAtualizacaoDTO usuarioDTO) {
        try {
            Usuario usuarioAtualizado = service.atualizar(id, usuarioDTO);
            UsuarioRetornadoDTO usuarioRetornado = new UsuarioRetornadoDTO(usuarioAtualizado.getId(), usuarioAtualizado.getNome(), usuarioAtualizado.getEmail());
            return new ResponseEntity<>(usuarioRetornado, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (ValidationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remover um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRetornadoDTO> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuarioBuscado = service.buscarPorId(id);
            UsuarioRetornadoDTO usuario = new UsuarioRetornadoDTO(usuarioBuscado.getId(), usuarioBuscado.getNome(), usuarioBuscado.getEmail());
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UsuarioListagemDTO>> listar() {
        List<UsuarioListagemDTO> usuarios = service.listar();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Fazer login
    @PostMapping("/login")
    public ResponseEntity<UsuarioRetornadoDTO> login(@RequestBody @Valid UsuarioLoginDTO loginDTO) {
        try {
            UsuarioRetornadoDTO usuario = service.login(loginDTO.email(), loginDTO.senha());
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (LoginFailedException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
