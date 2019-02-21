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

import com.intimamentecosmeticos.api.model.ProdutoVenda;
import com.intimamentecosmeticos.api.repository.ProdutoVendaRepository;
import com.intimamentecosmeticos.api.service.ProdutoVendaService;

@RestController
@RequestMapping("/produtovenda")
public class ProdutoVendaResource {
	
	@Autowired
	private ProdutoVendaRepository produtoVendaRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProdutoVendaService produtoVendaService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<ProdutoVenda> listar(){
		return produtoVendaRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<ProdutoVenda> produtosVendas = produtoVendaRepository.findAll();
		//return !produtosVendas.isEmpty() ? ResponseEntity.ok(produtosVendas) : ResponseEntity.notFound().build(); //404 Not Found
		return !produtosVendas.isEmpty() ? ResponseEntity.ok(produtosVendas) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<ProdutoVenda> criar(@Valid @RequestBody ProdutoVenda produtoVenda, HttpServletResponse response) {
		ProdutoVenda produtoVendaSalvo = produtoVendaRepository.save(produtoVenda);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoVendaSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoVendaSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public ProdutoVenda buscarPeloCodigo(@PathVariable Long codigo) {
		return produtoVendaRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o produtoVenda não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		ProdutoVenda produtoVenda = produtoVendaRepository.findOne(codigo);
		return !(produtoVenda == null) ? ResponseEntity.ok(produtoVenda) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		produtoVendaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<ProdutoVenda> atualizar(@PathVariable Long codigo, @Valid @RequestBody ProdutoVenda produtoVenda){
		ProdutoVenda produtoVendaSalva = produtoVendaService.atualizar(codigo, produtoVenda);
		
		return ResponseEntity.ok(produtoVendaSalva);
	}
}
