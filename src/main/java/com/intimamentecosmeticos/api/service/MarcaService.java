package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Marca;
import com.intimamentecosmeticos.api.repository.MarcaRepository;

@Service
public class MarcaService {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	public Marca atualizar(Long codigo, Marca marca) {
		Marca marcaSalva = marcaRepository.findOne(codigo);
		
		if(marcaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(marca, marcaSalva, "codigo"); //Copia as propriedades de marca para marcaSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return marcaRepository.save(marcaSalva);
	}
}
