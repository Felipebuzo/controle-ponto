package com.felipebuzo.controleponto.controller;

import com.felipebuzo.controleponto.dto.SolicitacaoAjusteRequest;
import com.felipebuzo.controleponto.model.RegistroPonto;
import com.felipebuzo.controleponto.model.SolicitacaoAjuste;
import com.felipebuzo.controleponto.model.StatusSolicitacao;
import com.felipebuzo.controleponto.model.TipoBatida;
import com.felipebuzo.controleponto.model.Usuario;
import com.felipebuzo.controleponto.repository.RegistroPontoRepository;
import com.felipebuzo.controleponto.repository.SolicitacaoAjusteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoAjusteController {

    private final SolicitacaoAjusteRepository solicitacaoAjusteRepository;
    private final RegistroPontoRepository registroPontoRepository;

    public SolicitacaoAjusteController(SolicitacaoAjusteRepository solicitacaoAjusteRepository,
                                         RegistroPontoRepository registroPontoRepository) {
        this.solicitacaoAjusteRepository = solicitacaoAjusteRepository;
        this.registroPontoRepository = registroPontoRepository;
    }

    @PostMapping
    public ResponseEntity<?> abrirSolicitacao(@RequestBody SolicitacaoAjusteRequest request, Authentication authentication) {
        Usuario funcionarioLogado = (Usuario) authentication.getPrincipal();

        SolicitacaoAjuste solicitacao = new SolicitacaoAjuste();
        solicitacao.setDia(request.getDia());
        solicitacao.setHorarioCorreto(request.getHorarioCorreto());
        solicitacao.setJustificativa(request.getJustificativa());
        solicitacao.setStatus(StatusSolicitacao.PENDENTE);
        solicitacao.setFuncionario(funcionarioLogado);

        solicitacaoAjusteRepository.save(solicitacao);

        return ResponseEntity.ok(solicitacao);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<?> listarPendentes() {
        List<SolicitacaoAjuste> pendentes = solicitacaoAjusteRepository.findByStatus(StatusSolicitacao.PENDENTE);
        return ResponseEntity.ok(pendentes);
    }

    @PatchMapping("/{id}/aprovar")
public ResponseEntity<?> aprovar(@PathVariable Long id, Authentication authentication) {
    Usuario gestorLogado = (Usuario) authentication.getPrincipal();

    Optional<SolicitacaoAjuste> solicitacaoOpt = solicitacaoAjusteRepository.findById(id);

    if (solicitacaoOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Solicitação não encontrada");
    }

    SolicitacaoAjuste solicitacao = solicitacaoOpt.get();
    solicitacao.setStatus(StatusSolicitacao.APROVADA);
    solicitacao.setGestor(gestorLogado);
    solicitacaoAjusteRepository.save(solicitacao);

    RegistroPonto registro = new RegistroPonto();
    registro.setTipo(TipoBatida.ENTRADA);
    registro.setDataHora(LocalDateTime.of(solicitacao.getDia(), solicitacao.getHorarioCorreto()));
    registro.setUsuario(solicitacao.getFuncionario());
    registroPontoRepository.save(registro);

    return ResponseEntity.ok(solicitacao);
}

@PatchMapping("/{id}/rejeitar")
public ResponseEntity<?> rejeitar(@PathVariable Long id, Authentication authentication) {
    Usuario gestorLogado = (Usuario) authentication.getPrincipal();

    Optional<SolicitacaoAjuste> solicitacaoOpt = solicitacaoAjusteRepository.findById(id);

    if (solicitacaoOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Solicitação não encontrada");
    }

    SolicitacaoAjuste solicitacao = solicitacaoOpt.get();
    solicitacao.setStatus(StatusSolicitacao.REJEITADA);
    solicitacao.setGestor(gestorLogado);
    solicitacaoAjusteRepository.save(solicitacao);

    return ResponseEntity.ok(solicitacao);
}

}