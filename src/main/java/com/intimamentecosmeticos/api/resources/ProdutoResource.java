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

import com.intimamentecosmeticos.api.model.Produto;
import com.intimamentecosmeticos.api.repository.ProdutoRepository;
import com.intimamentecosmeticos.api.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProdutoService produtoService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Produto> listar(){
		return produtoRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Produto> produtos = produtoRepository.findAll();
		//return !produtos.isEmpty() ? ResponseEntity.ok(produtos) : ResponseEntity.notFound().build(); //404 Not Found
		return !produtos.isEmpty() ? ResponseEntity.ok(produtos) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto, HttpServletResponse response) {
		Produto produtoSalvo = produtoRepository.save(produto);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Produto buscarPeloCodigo(@PathVariable Long codigo) {
		return produtoRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o produto não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Produto produto = produtoRepository.findOne(codigo);
		return !(produto == null) ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		produtoRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigo, @Valid @RequestBody Produto produto){
		Produto produtoSalva = produtoService.atualizar(codigo, produto);
		
		return ResponseEntity.ok(produtoSalva);
	}
}
