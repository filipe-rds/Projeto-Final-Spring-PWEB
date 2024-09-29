package com.api.users.entity;

import com.api.users.dto.UsuarioRecebidoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;

    public Usuario (UsuarioRecebidoDTO dto){
        this.nome = dto.nome();
        this.email = dto.email();
        this.senha = dto.senha();
    }
}
