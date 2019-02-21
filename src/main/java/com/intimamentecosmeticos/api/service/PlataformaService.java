package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Plataforma;
import com.intimamentecosmeticos.api.repository.PlataformaRepository;

@Service
public class PlataformaService {
	
	@Autowired
	private PlataformaRepository plataformaRepository;
	
	public Plataforma atualizar(Long codigo, Plataforma plataforma) {
		Plataforma plataformaSalva = plataformaRepository.findOne(codigo);
		
		if(plataformaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(plataforma, plataformaSalva, "codigo"); //Copia as propriedades de plataforma para plataformaSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return plataformaRepository.save(plataformaSalva);
	}
}
