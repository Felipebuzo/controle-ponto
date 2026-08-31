package com.felipebuzo.controleponto.dto;

import com.felipebuzo.controleponto.model.TipoBatida;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaterPontoRequest {

    private TipoBatida tipo;
}