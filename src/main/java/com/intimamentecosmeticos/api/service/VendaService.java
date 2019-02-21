package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Venda;
import com.intimamentecosmeticos.api.repository.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	public Venda atualizar(Long codigo, Venda venda) {
		Venda vendaSalva = vendaRepository.findOne(codigo);
		
		if(vendaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(venda, vendaSalva, "codigo"); //Copia as propriedades de venda para vendaSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return vendaRepository.save(vendaSalva);
	}
}
