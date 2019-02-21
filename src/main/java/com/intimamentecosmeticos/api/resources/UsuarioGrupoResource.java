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

import com.intimamentecosmeticos.api.model.UsuarioGrupo;
import com.intimamentecosmeticos.api.repository.UsuarioGrupoRepository;
import com.intimamentecosmeticos.api.service.UsuarioGrupoService;

@RestController
@RequestMapping("/usuariogrupo")
public class UsuarioGrupoResource {
	
	@Autowired
	private UsuarioGrupoRepository usuarioGrupoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private UsuarioGrupoService usuarioGrupoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<UsuarioGrupo> listar(){
		return usuarioGrupoRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<UsuarioGrupo> produtosVendas = usuarioGrupoRepository.findAll();
		//return !produtosVendas.isEmpty() ? ResponseEntity.ok(produtosVendas) : ResponseEntity.notFound().build(); //404 Not Found
		return !produtosVendas.isEmpty() ? ResponseEntity.ok(produtosVendas) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<UsuarioGrupo> criar(@Valid @RequestBody UsuarioGrupo usuarioGrupo, HttpServletResponse response) {
		UsuarioGrupo usuarioGrupoSalvo = usuarioGrupoRepository.save(usuarioGrupo);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioGrupoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGrupoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public UsuarioGrupo buscarPeloCodigo(@PathVariable Long codigo) {
		return usuarioGrupoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o usuarioGrupo não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		UsuarioGrupo usuarioGrupo = usuarioGrupoRepository.findOne(codigo);
		return !(usuarioGrupo == null) ? ResponseEntity.ok(usuarioGrupo) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		usuarioGrupoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<UsuarioGrupo> atualizar(@PathVariable Long codigo, @Valid @RequestBody UsuarioGrupo usuarioGrupo){
		UsuarioGrupo usuarioGrupoSalva = usuarioGrupoService.atualizar(codigo, usuarioGrupo);
		
		return ResponseEntity.ok(usuarioGrupoSalva);
	}
}
