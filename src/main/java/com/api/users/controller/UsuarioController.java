package com.api.users.controller;

import com.api.users.dto.UsuarioDTO;
import com.api.users.dto.UsuarioListagemDTO;
import com.api.users.dto.UsuarioLoginDTO;
import com.api.users.dto.UsuarioRetornadoDTO;
import com.api.users.entity.Usuario;
import com.api.users.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Adicionar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> adicionar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioAdicionado = service.adicionar(usuarioDTO);
            return new ResponseEntity<>(usuarioAdicionado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    // Atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = service.buscarPorId(id);
        if (usuarioExistente != null) {
            usuario.setId(id); // Certificar-se de que o ID não seja alterado
            Usuario usuarioAtualizado = service.atualizar(usuario);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
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
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = service.buscarPorId(id);
        if (usuario != null) {
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
    public ResponseEntity<UsuarioRetornadoDTO> login(@RequestBody UsuarioLoginDTO loginDTO) {
        try {
            UsuarioRetornadoDTO usuario = service.login(loginDTO.email(), loginDTO.senha());
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}