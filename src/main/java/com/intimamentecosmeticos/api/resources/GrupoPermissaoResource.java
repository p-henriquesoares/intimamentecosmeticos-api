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

import com.intimamentecosmeticos.api.model.GrupoPermissao;
import com.intimamentecosmeticos.api.repository.GrupoPermissaoRepository;
import com.intimamentecosmeticos.api.service.GrupoPermissaoService;

@RestController
@RequestMapping("/grupopermissao")
public class GrupoPermissaoResource {
	
	@Autowired
	private GrupoPermissaoRepository grupoPermissaoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private GrupoPermissaoService grupoPermissaoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<GrupoPermissao> listar(){
		return grupoPermissaoRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<GrupoPermissao> gruposPermissoes = grupoPermissaoRepository.findAll();
		//return !gruposPermissoes.isEmpty() ? ResponseEntity.ok(gruposPermissoes) : ResponseEntity.notFound().build(); //404 Not Found
		return !gruposPermissoes.isEmpty() ? ResponseEntity.ok(gruposPermissoes) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<GrupoPermissao> criar(@Valid @RequestBody GrupoPermissao grupoPermissao, HttpServletResponse response) {
		GrupoPermissao grupoPermissaoSalvo = grupoPermissaoRepository.save(grupoPermissao);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, grupoPermissaoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(grupoPermissaoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public GrupoPermissao buscarPeloCodigo(@PathVariable Long codigo) {
		return grupoPermissaoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o grupoPermissao não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		GrupoPermissao grupoPermissao = grupoPermissaoRepository.findOne(codigo);
		return !(grupoPermissao == null) ? ResponseEntity.ok(grupoPermissao) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		grupoPermissaoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<GrupoPermissao> atualizar(@PathVariable Long codigo, @Valid @RequestBody GrupoPermissao grupoPermissao){
		GrupoPermissao grupoPermissaoSalva = grupoPermissaoService.atualizar(codigo, grupoPermissao);
		
		return ResponseEntity.ok(grupoPermissaoSalva);
	}
}
