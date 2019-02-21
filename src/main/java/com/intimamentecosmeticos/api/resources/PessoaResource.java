package com.intimamentecosmeticos.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intimamentecosmeticos.api.model.Pessoa;
import com.intimamentecosmeticos.api.repository.PessoaRepository;
import com.intimamentecosmeticos.api.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService pessoaService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		//return !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) : ResponseEntity.notFound().build(); //404 Not Found
		return !pessoas.isEmpty() ? ResponseEntity.ok(pessoas) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalvo = pessoaRepository.save(pessoa);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Pessoa buscarPeloCodigo(@PathVariable Long codigo) {
		return pessoaRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o pessoa não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		return !(pessoa == null) ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
		
		return ResponseEntity.ok(pessoaSalva);
	}
}
