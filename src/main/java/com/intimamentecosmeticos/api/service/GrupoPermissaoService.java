package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.GrupoPermissao;
import com.intimamentecosmeticos.api.repository.GrupoPermissaoRepository;

@Service
public class GrupoPermissaoService {
	
	@Autowired
	private GrupoPermissaoRepository grupoPermissaoRepository;
	
	public GrupoPermissao atualizar(Long codigo, GrupoPermissao grupoPermissao) {
		GrupoPermissao grupoPermissaoSalva = grupoPermissaoRepository.findOne(codigo);
		
		if(grupoPermissaoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(grupoPermissao, grupoPermissaoSalva, "codigo"); //Copia as propriedades de grupoPermissao para grupoPermissaoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return grupoPermissaoRepository.save(grupoPermissaoSalva);
	}
}
