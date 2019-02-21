package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Pais;
import com.intimamentecosmeticos.api.repository.PaisRepository;

@Service
public class PaisService {
	
	@Autowired
	private PaisRepository paisRepository;
	
	public Pais atualizar(Long codigo, Pais pais) {
		Pais paisSalva = paisRepository.findOne(codigo);
		
		if(paisSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(pais, paisSalva, "codigo"); //Copia as propriedades de pais para paisSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return paisRepository.save(paisSalva);
	}
}
