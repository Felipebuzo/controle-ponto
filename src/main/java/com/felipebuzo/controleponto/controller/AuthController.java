package com.felipebuzo.controleponto.controller;

import com.felipebuzo.controleponto.dto.LoginRequest;
import com.felipebuzo.controleponto.dto.LoginResponse;
import com.felipebuzo.controleponto.model.Usuario;
import com.felipebuzo.controleponto.repository.UsuarioRepository;
import com.felipebuzo.controleponto.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/gerar-hash")
    public ResponseEntity<?> gerarHash(@RequestBody LoginRequest request) {
        String hash = passwordEncoder.encode(request.getSenha());
        return ResponseEntity.ok(hash);
    }
}