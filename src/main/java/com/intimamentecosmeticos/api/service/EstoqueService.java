package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Estoque;
import com.intimamentecosmeticos.api.repository.EstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	public Estoque atualizar(Long codigo, Estoque estoque) {
		Estoque estoqueSalva = estoqueRepository.findOne(codigo);
		
		if(estoqueSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(estoque, estoqueSalva, "codigo"); //Copia as propriedades de estoque para estoqueSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return estoqueRepository.save(estoqueSalva);
	}
}
