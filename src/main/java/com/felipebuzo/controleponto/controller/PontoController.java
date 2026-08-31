package com.felipebuzo.controleponto.controller;

import com.felipebuzo.controleponto.dto.BaterPontoRequest;
import com.felipebuzo.controleponto.model.RegistroPonto;
import com.felipebuzo.controleponto.model.Usuario;
import com.felipebuzo.controleponto.repository.RegistroPontoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ponto")
public class PontoController {

    private final RegistroPontoRepository registroPontoRepository;

    public PontoController(RegistroPontoRepository registroPontoRepository) {
        this.registroPontoRepository = registroPontoRepository;
    }

    @PostMapping
    public ResponseEntity<?> baterPonto(@RequestBody BaterPontoRequest request, Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        RegistroPonto registro = new RegistroPonto();
        registro.setTipo(request.getTipo());
        registro.setDataHora(LocalDateTime.now());
        registro.setUsuario(usuarioLogado);

        registroPontoRepository.save(registro);

        return ResponseEntity.ok(registro);
    }

    @GetMapping("/meu-historico")
    public ResponseEntity<?> meuHistorico(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        List<RegistroPonto> historico = registroPontoRepository.findByUsuarioOrderByDataHoraDesc(usuarioLogado);

        return ResponseEntity.ok(historico);
    }
}