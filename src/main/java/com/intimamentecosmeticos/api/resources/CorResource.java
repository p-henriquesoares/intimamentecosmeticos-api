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

import com.intimamentecosmeticos.api.model.Cor;
import com.intimamentecosmeticos.api.repository.CorRepository;
import com.intimamentecosmeticos.api.service.CorService;

@RestController
@RequestMapping("/cor")
public class CorResource {
	
	@Autowired
	private CorRepository corRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private CorService corService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Cor> listar(){
		return corRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Cor> cores = corRepository.findAll();
		//return !cores.isEmpty() ? ResponseEntity.ok(cores) : ResponseEntity.notFound().build(); //404 Not Found
		return !cores.isEmpty() ? ResponseEntity.ok(cores) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Cor> criar(@Valid @RequestBody Cor cor, HttpServletResponse response) {
		Cor corSalvo = corRepository.save(cor);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, corSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(corSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Cor buscarPeloCodigo(@PathVariable Long codigo) {
		return corRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o cor não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Cor cor = corRepository.findOne(codigo);
		return !(cor == null) ? ResponseEntity.ok(cor) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		corRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cor> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cor cor){
		Cor corSalva = corService.atualizar(codigo, cor);
		
		return ResponseEntity.ok(corSalva);
	}
}
