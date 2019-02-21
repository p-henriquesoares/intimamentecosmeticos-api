package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Permissao;
import com.intimamentecosmeticos.api.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao atualizar(Long codigo, Permissao permissao) {
		Permissao permissaoSalva = permissaoRepository.findOne(codigo);
		
		if(permissaoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(permissao, permissaoSalva, "codigo"); //Copia as propriedades de permissao para permissaoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return permissaoRepository.save(permissaoSalva);
	}
}
