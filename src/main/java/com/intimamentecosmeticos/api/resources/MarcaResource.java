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

import com.intimamentecosmeticos.api.model.Marca;
import com.intimamentecosmeticos.api.repository.MarcaRepository;
import com.intimamentecosmeticos.api.service.MarcaService;

@RestController
@RequestMapping("/marca")
public class MarcaResource {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	private MarcaService marcaService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Marca> listar(){
		return marcaRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Marca> marcas = marcaRepository.findAll();
		//return !marcas.isEmpty() ? ResponseEntity.ok(marcas) : ResponseEntity.notFound().build(); //404 Not Found
		return !marcas.isEmpty() ? ResponseEntity.ok(marcas) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Marca> criar(@Valid @RequestBody Marca marca, HttpServletResponse response) {
		Marca marcaSalvo = marcaRepository.save(marca);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, marcaSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(marcaSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Marca buscarPeloCodigo(@PathVariable Long codigo) {
		return marcaRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o marca não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Marca marca = marcaRepository.findOne(codigo);
		return !(marca == null) ? ResponseEntity.ok(marca) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		marcaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Marca> atualizar(@PathVariable Long codigo, @Valid @RequestBody Marca marca){
		Marca marcaSalva = marcaService.atualizar(codigo, marca);
		
		return ResponseEntity.ok(marcaSalva);
	}
}
