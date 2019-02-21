package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Grupo;
import com.intimamentecosmeticos.api.repository.GrupoRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	public Grupo atualizar(Long codigo, Grupo grupo) {
		Grupo grupoSalva = grupoRepository.findOne(codigo);
		
		if(grupoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(grupo, grupoSalva, "codigo"); //Copia as propriedades de grupo para grupoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return grupoRepository.save(grupoSalva);
	}
}
