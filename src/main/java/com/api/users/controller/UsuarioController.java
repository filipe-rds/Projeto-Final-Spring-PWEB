package com.api.users.controller;

import com.api.users.dto.*;
import com.api.users.entity.Usuario;
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
    public ResponseEntity<UsuarioRetornadoDTO> adicionar(@RequestBody @Valid UsuarioRecebidoDTO usuarioDTO) {
        try {
            UsuarioRetornadoDTO usuarioAdicionado = service.adicionar(usuarioDTO);
            return new ResponseEntity<>(usuarioAdicionado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    // Atualizar um usuário existente
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioRetornadoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRecebidoDTO usuarioDTO) {
        Usuario usuarioAtualizado = service.atualizar(id, usuarioDTO);
        if (usuarioAtualizado != null) {
            UsuarioRetornadoDTO usuario = new UsuarioRetornadoDTO(usuarioAtualizado.getId(), usuarioAtualizado.getNome(),usuarioAtualizado.getEmail());

            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Remover um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Usuario usuario = service.buscarPorId(id);
        if (usuario != null) {
            service.remover(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRetornadoDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuarioBuscado = service.buscarPorId(id);

        if (usuarioBuscado != null) {
            UsuarioRetornadoDTO usuario = new UsuarioRetornadoDTO(usuarioBuscado.getId(), usuarioBuscado.getNome(), usuarioBuscado.getEmail());
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
