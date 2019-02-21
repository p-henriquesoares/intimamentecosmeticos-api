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

import com.intimamentecosmeticos.api.model.Cidade;
import com.intimamentecosmeticos.api.repository.CidadeRepository;
import com.intimamentecosmeticos.api.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeResource {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private CidadeService cidadeService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Cidade> cidades = cidadeRepository.findAll();
		//return !cidades.isEmpty() ? ResponseEntity.ok(cidades) : ResponseEntity.notFound().build(); //404 Not Found
		return !cidades.isEmpty() ? ResponseEntity.ok(cidades) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Cidade> criar(@Valid @RequestBody Cidade cidade, HttpServletResponse response) {
		Cidade cidadeSalvo = cidadeRepository.save(cidade);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, cidadeSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Cidade buscarPeloCodigo(@PathVariable Long codigo) {
		return cidadeRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o cidade não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Cidade cidade = cidadeRepository.findOne(codigo);
		return !(cidade == null) ? ResponseEntity.ok(cidade) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		cidadeRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cidade> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cidade cidade){
		Cidade cidadeSalva = cidadeService.atualizar(codigo, cidade);
		
		return ResponseEntity.ok(cidadeSalva);
	}
}
