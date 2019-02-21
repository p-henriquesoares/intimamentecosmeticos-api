package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Lancamento;
import com.intimamentecosmeticos.api.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalva = lancamentoRepository.findOne(codigo);
		
		if(lancamentoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo"); //Copia as propriedades de lancamento para lancamentoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return lancamentoRepository.save(lancamentoSalva);
	}
}
