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

import com.intimamentecosmeticos.api.model.Pais;
import com.intimamentecosmeticos.api.repository.PaisRepository;
import com.intimamentecosmeticos.api.service.PaisService;

@RestController
@RequestMapping("/pais")
public class PaisResource {
	
	@Autowired
	private PaisRepository paisRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private PaisService paisService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Pais> listar(){
		return paisRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Pais> paises = paisRepository.findAll();
		//return !paises.isEmpty() ? ResponseEntity.ok(paises) : ResponseEntity.notFound().build(); //404 Not Found
		return !paises.isEmpty() ? ResponseEntity.ok(paises) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Pais> criar(@Valid @RequestBody Pais pais, HttpServletResponse response) {
		Pais paisSalvo = paisRepository.save(pais);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, paisSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(paisSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Pais buscarPeloCodigo(@PathVariable Long codigo) {
		return paisRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o pais não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Pais pais = paisRepository.findOne(codigo);
		return !(pais == null) ? ResponseEntity.ok(pais) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		paisRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pais> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pais pais){
		Pais paisSalva = paisService.atualizar(codigo, pais);
		
		return ResponseEntity.ok(paisSalva);
	}
}
