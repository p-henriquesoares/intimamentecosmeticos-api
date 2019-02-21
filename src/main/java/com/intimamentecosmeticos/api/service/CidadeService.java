package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Cidade;
import com.intimamentecosmeticos.api.repository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cidade atualizar(Long codigo, Cidade cidade) {
		Cidade cidadeSalva = cidadeRepository.findOne(codigo);
		
		if(cidadeSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(cidade, cidadeSalva, "codigo"); //Copia as propriedades de cidade para cidadeSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return cidadeRepository.save(cidadeSalva);
	}
}
