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

import com.intimamentecosmeticos.api.model.Permissao;
import com.intimamentecosmeticos.api.repository.PermissaoRepository;
import com.intimamentecosmeticos.api.service.PermissaoService;

@RestController
@RequestMapping("/permissao")
public class PermissaoResource {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private PermissaoService permissaoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Permissao> listar(){
		return permissaoRepository.findAll();
	};
	
	//Retorna de apermissaodo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Permissao> permissoes = permissaoRepository.findAll();
		//return !permissoes.isEmpty() ? ResponseEntity.ok(permissoes) : ResponseEntity.notFound().build(); //404 Not Found
		return !permissoes.isEmpty() ? ResponseEntity.ok(permissoes) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Permissao> criar(@Valid @RequestBody Permissao permissao, HttpServletResponse response) {
		Permissao permissaoSalvo = permissaoRepository.save(permissao);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, permissaoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(permissaoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Permissao buscarPeloCodigo(@PathVariable Long codigo) {
		return permissaoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o permissao não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Permissao permissao = permissaoRepository.findOne(codigo);
		return !(permissao == null) ? ResponseEntity.ok(permissao) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		permissaoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Permissao> atualizar(@PathVariable Long codigo, @Valid @RequestBody Permissao permissao){
		Permissao permissaoSalva = permissaoService.atualizar(codigo, permissao);
		
		return ResponseEntity.ok(permissaoSalva);
	}
}
