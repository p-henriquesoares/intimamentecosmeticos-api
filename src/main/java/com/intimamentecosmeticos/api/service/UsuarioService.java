package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Usuario;
import com.intimamentecosmeticos.api.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalva = buscarUsuarioPeloCodigo(codigo);
		
		BeanUtils.copyProperties(usuario, usuarioSalva, "codigo"); //Copia as propriedades de usuario para usuarioSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return usuarioRepository.save(usuarioSalva);
	}
	
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Usuario usuarioSalva = buscarUsuarioPeloCodigo(codigo);
		usuarioSalva.setAtivo(ativo);
		usuarioRepository.save(usuarioSalva);
	}

	private Usuario buscarUsuarioPeloCodigo(Long codigo) {
		Usuario usuarioSalva = usuarioRepository.findOne(codigo);
		
		if(usuarioSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return usuarioSalva;
	}
}
