package com.felipebuzo.controleponto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SolicitacaoAjusteRequest {

    private LocalDate dia;
    private LocalTime horarioCorreto;
    private String justificativa;
}