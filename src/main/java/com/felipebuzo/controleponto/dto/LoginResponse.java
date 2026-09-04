package com.felipebuzo.controleponto.dto;

import com.felipebuzo.controleponto.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long id;
    private String nome;
    private String email;
    private String papel;

    public LoginResponse(String token, Usuario usuario) {
        this.token = token;
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.papel = usuario.getPapel().name();
    }
}