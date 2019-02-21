package com.intimamentecosmeticos.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intimamentecosmeticos.api.model.Erro;
import com.intimamentecosmeticos.api.repository.ErroRepository;

@RestController
@RequestMapping("/erro")
public class ErroResource {
	
	@Autowired
	private ErroRepository erroRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@PostMapping
	public void criar(Erro erro) {
		erroRepository.save(erro);
	}
}
