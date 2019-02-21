package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Estado;
import com.intimamentecosmeticos.api.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado atualizar(Long codigo, Estado estado) {
		Estado estadoSalva = estadoRepository.findOne(codigo);
		
		if(estadoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(estado, estadoSalva, "codigo"); //Copia as propriedades de estado para estadoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return estadoRepository.save(estadoSalva);
	}
}
