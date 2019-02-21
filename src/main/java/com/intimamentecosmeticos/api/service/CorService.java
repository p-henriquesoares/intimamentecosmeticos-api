package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Cor;
import com.intimamentecosmeticos.api.repository.CorRepository;

@Service
public class CorService {
	
	@Autowired
	private CorRepository corRepository;
	
	public Cor atualizar(Long codigo, Cor cor) {
		Cor corSalva = corRepository.findOne(codigo);
		
		if(corSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(cor, corSalva, "codigo"); //Copia as propriedades de cor para corSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return corRepository.save(corSalva);
	}
}
