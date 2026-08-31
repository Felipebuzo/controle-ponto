package com.felipebuzo.controleponto.repository;

import com.felipebuzo.controleponto.model.RegistroPonto;
import com.felipebuzo.controleponto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Long> {

    List<RegistroPonto> findByUsuarioOrderByDataHoraDesc(Usuario usuario);
}