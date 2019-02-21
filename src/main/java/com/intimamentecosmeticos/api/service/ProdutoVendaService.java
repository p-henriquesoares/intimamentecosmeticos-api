package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.ProdutoVenda;
import com.intimamentecosmeticos.api.repository.ProdutoVendaRepository;

@Service
public class ProdutoVendaService {
	
	@Autowired
	private ProdutoVendaRepository produtoVendaRepository;
	
	public ProdutoVenda atualizar(Long codigo, ProdutoVenda produtoVenda) {
		ProdutoVenda produtoVendaSalva = produtoVendaRepository.findOne(codigo);
		
		if(produtoVendaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(produtoVenda, produtoVendaSalva, "codigo"); //Copia as propriedades de produtoVenda para produtoVendaSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return produtoVendaRepository.save(produtoVendaSalva);
	}
}
