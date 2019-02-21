package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Produto;
import com.intimamentecosmeticos.api.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoSalva = produtoRepository.findOne(codigo);
		
		if(produtoSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(produto, produtoSalva, "codigo"); //Copia as propriedades de produto para produtoSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return produtoRepository.save(produtoSalva);
	}
}
