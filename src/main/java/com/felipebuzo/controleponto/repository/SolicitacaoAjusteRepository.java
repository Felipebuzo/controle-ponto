package com.felipebuzo.controleponto.repository;

import com.felipebuzo.controleponto.model.SolicitacaoAjuste;
import com.felipebuzo.controleponto.model.StatusSolicitacao;
import com.felipebuzo.controleponto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoAjusteRepository extends JpaRepository<SolicitacaoAjuste, Long> {

    List<SolicitacaoAjuste> findByFuncionarioOrderByDiaDesc(Usuario funcionario);

    List<SolicitacaoAjuste> findByStatus(StatusSolicitacao status);
}