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

import com.intimamentecosmeticos.api.model.Grupo;
import com.intimamentecosmeticos.api.repository.GrupoRepository;
import com.intimamentecosmeticos.api.service.GrupoService;

@RestController
@RequestMapping("/grupo")
public class GrupoResource {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private GrupoService grupoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Grupo> listar(){
		return grupoRepository.findAll();
	};
	
	//Retorna de agrupodo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Grupo> grupos = grupoRepository.findAll();
		//return !grupos.isEmpty() ? ResponseEntity.ok(grupos) : ResponseEntity.notFound().build(); //404 Not Found
		return !grupos.isEmpty() ? ResponseEntity.ok(grupos) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Grupo> criar(@Valid @RequestBody Grupo grupo, HttpServletResponse response) {
		Grupo grupoSalvo = grupoRepository.save(grupo);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, grupoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(grupoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Grupo buscarPeloCodigo(@PathVariable Long codigo) {
		return grupoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o grupo não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Grupo grupo = grupoRepository.findOne(codigo);
		return !(grupo == null) ? ResponseEntity.ok(grupo) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		grupoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Grupo> atualizar(@PathVariable Long codigo, @Valid @RequestBody Grupo grupo){
		Grupo grupoSalva = grupoService.atualizar(codigo, grupo);
		
		return ResponseEntity.ok(grupoSalva);
	}
}
