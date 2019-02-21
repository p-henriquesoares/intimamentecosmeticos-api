package com.intimamentecosmeticos.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.intimamentecosmeticos.api.model.Lancamento;
import com.intimamentecosmeticos.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

		public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
