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

import com.intimamentecosmeticos.api.model.Plataforma;
import com.intimamentecosmeticos.api.repository.PlataformaRepository;
import com.intimamentecosmeticos.api.service.PlataformaService;

@RestController
@RequestMapping("/plataforma")
public class PlataformaResource {
	
	@Autowired
	private PlataformaRepository plataformaRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private PlataformaService plataformaService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Plataforma> listar(){
		return plataformaRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Plataforma> plataformas = plataformaRepository.findAll();
		//return !plataformas.isEmpty() ? ResponseEntity.ok(plataformas) : ResponseEntity.notFound().build(); //404 Not Found
		return !plataformas.isEmpty() ? ResponseEntity.ok(plataformas) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Plataforma> criar(@Valid @RequestBody Plataforma plataforma, HttpServletResponse response) {
		Plataforma plataformaSalvo = plataformaRepository.save(plataforma);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, plataformaSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(plataformaSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Plataforma buscarPeloCodigo(@PathVariable Long codigo) {
		return plataformaRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o plataforma não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Plataforma plataforma = plataformaRepository.findOne(codigo);
		return !(plataforma == null) ? ResponseEntity.ok(plataforma) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		plataformaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Plataforma> atualizar(@PathVariable Long codigo, @Valid @RequestBody Plataforma plataforma){
		Plataforma plataformaSalva = plataformaService.atualizar(codigo, plataforma);
		
		return ResponseEntity.ok(plataformaSalva);
	}
}
