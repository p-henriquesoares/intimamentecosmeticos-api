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

import com.intimamentecosmeticos.api.model.Estado;
import com.intimamentecosmeticos.api.repository.EstadoRepository;
import com.intimamentecosmeticos.api.service.EstadoService;

@RestController
@RequestMapping("/estado")
public class EstadoResource {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private EstadoService estadoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Estado> estados = estadoRepository.findAll();
		//return !estados.isEmpty() ? ResponseEntity.ok(estados) : ResponseEntity.notFound().build(); //404 Not Found
		return !estados.isEmpty() ? ResponseEntity.ok(estados) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Estado> criar(@Valid @RequestBody Estado estado, HttpServletResponse response) {
		Estado estadoSalvo = estadoRepository.save(estado);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, estadoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(estadoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Estado buscarPeloCodigo(@PathVariable Long codigo) {
		return estadoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o estado não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Estado estado = estadoRepository.findOne(codigo);
		return !(estado == null) ? ResponseEntity.ok(estado) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		estadoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long codigo, @Valid @RequestBody Estado estado){
		Estado estadoSalva = estadoService.atualizar(codigo, estado);
		
		return ResponseEntity.ok(estadoSalva);
	}
}
