package com.intimamentecosmeticos.api.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.intimamentecosmeticos.api.model.Lancamento;
import com.intimamentecosmeticos.api.repository.LancamentoRepository;
import com.intimamentecosmeticos.api.repository.filter.LancamentoFilter;
import com.intimamentecosmeticos.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamento")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Lancamento> lancamentos = lancamentoRepository.findAll();
		//return !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.notFound().build(); //404 Not Found
		return !lancamentos.isEmpty() ? ResponseEntity.ok(lancamentos) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Lancamento buscarPeloCodigo(@PathVariable Long codigo) {
		return lancamentoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o lancamento não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		return !(lancamento == null) ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento){
		Lancamento lancamentoSalva = lancamentoService.atualizar(codigo, lancamento);
		
		return ResponseEntity.ok(lancamentoSalva);
	}
}
