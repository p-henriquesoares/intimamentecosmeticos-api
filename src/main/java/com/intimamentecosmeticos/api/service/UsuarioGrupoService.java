package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.UsuarioGrupo;
import com.intimamentecosmeticos.api.repository.UsuarioGrupoRepository;

@Service
public class UsuarioGrupoService {
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	public UsuarioGrupo atualizar(Long codigo, UsuarioGrupo usuarioGrupo) {
		UsuarioGrupo usuarioGrupoSalva = usuarioGrupoRepository.findOne(codigo);
		
		if(usuarioGrupoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(usuarioGrupo, usuarioGrupoSalva, "codigo"); //Copia as propriedades de usuarioGrupo para usuarioGrupoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return usuarioGrupoRepository.save(usuarioGrupoSalva);
	}
}
