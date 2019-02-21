package com.intimamentecosmeticos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intimamentecosmeticos.api.model.Lancamento;
import com.intimamentecosmeticos.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
