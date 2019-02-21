package com.intimamentecosmeticos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.intimamentecosmeticos.api.model.Cliente;
import com.intimamentecosmeticos.api.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente atualizar(Long codigo, Cliente cliente) {
		Cliente clienteSalva = clienteRepository.findOne(codigo);
		
		if(clienteSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(cliente, clienteSalva, "codigo"); //Copia as propriedades de cliente para clienteSalva com exceção do código pois na atualização não é passado o código, a URL possui o código.
		
		return clienteRepository.save(clienteSalva);
	}
}
