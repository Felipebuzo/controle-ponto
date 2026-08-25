package com.felipebuzo.controleponto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "solicitacoes_ajuste")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoAjuste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dia;

    private LocalTime horarioCorreto;

    private String justificativa;

    @Enumerated(EnumType.STRING)
    private StatusSolicitacao status;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Usuario funcionario;

    @ManyToOne
    @JoinColumn(name = "gestor_id")
    private Usuario gestor;
}