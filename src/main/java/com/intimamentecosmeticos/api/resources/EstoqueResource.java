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

import com.intimamentecosmeticos.api.model.Estoque;
import com.intimamentecosmeticos.api.repository.EstoqueRepository;
import com.intimamentecosmeticos.api.service.EstoqueService;

@RestController
@RequestMapping("/estoque")
public class EstoqueResource {
	
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private EstoqueService estoqueService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Estoque> listar(){
		return estoqueRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Estoque> estoques = estoqueRepository.findAll();
		//return !estoques.isEmpty() ? ResponseEntity.ok(estoques) : ResponseEntity.notFound().build(); //404 Not Found
		return !estoques.isEmpty() ? ResponseEntity.ok(estoques) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Estoque> criar(@Valid @RequestBody Estoque estoque, HttpServletResponse response) {
		Estoque estoqueSalvo = estoqueRepository.save(estoque);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, estoqueSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(estoqueSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Estoque buscarPeloCodigo(@PathVariable Long codigo) {
		return estoqueRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o estoque não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Estoque estoque = estoqueRepository.findOne(codigo);
		return !(estoque == null) ? ResponseEntity.ok(estoque) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		estoqueRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Estoque> atualizar(@PathVariable Long codigo, @Valid @RequestBody Estoque estoque){
		Estoque estoqueSalva = estoqueService.atualizar(codigo, estoque);
		
		return ResponseEntity.ok(estoqueSalva);
	}
}
