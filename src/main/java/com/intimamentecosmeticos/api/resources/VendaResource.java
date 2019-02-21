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

import com.intimamentecosmeticos.api.model.Venda;
import com.intimamentecosmeticos.api.repository.VendaRepository;
import com.intimamentecosmeticos.api.service.VendaService;

@RestController
@RequestMapping("/venda")
public class VendaResource {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private VendaService vendaService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Venda> listar(){
		return vendaRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Venda> vendas = vendaRepository.findAll();
		//return !vendas.isEmpty() ? ResponseEntity.ok(vendas) : ResponseEntity.notFound().build(); //404 Not Found
		return !vendas.isEmpty() ? ResponseEntity.ok(vendas) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Venda> criar(@Valid @RequestBody Venda venda, HttpServletResponse response) {
		Venda vendaSalvo = vendaRepository.save(venda);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Venda buscarPeloCodigo(@PathVariable Long codigo) {
		return vendaRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o venda não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Venda venda = vendaRepository.findOne(codigo);
		return !(venda == null) ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build(); //404 Not Found
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		vendaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Venda> atualizar(@PathVariable Long codigo, @Valid @RequestBody Venda venda){
		Venda vendaSalva = vendaService.atualizar(codigo, venda);
		
		return ResponseEntity.ok(vendaSalva);
	}
}
