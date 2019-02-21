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

import com.intimamentecosmeticos.api.model.Cliente;
import com.intimamentecosmeticos.api.repository.ClienteRepository;
import com.intimamentecosmeticos.api.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	//O próprio Spring já está adicionando o location no Header após o POST do recurso.
	//@Autowired
	//private ApplicationEventPublisher publisher;
	
	@Autowired
	private ClienteService clienteService;
	
	//Retorna uma lista vazia em JSON caso não haja dados.
	@GetMapping
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	};
	
	//Retorna de acordo com os retornos abaixo se não houver dados:
	/*@GetMapping
	public ResponseEntity<?> listar(){
		List<Cliente> clientes = clienteRepository.findAll();
		//return !clientes.isEmpty() ? ResponseEntity.ok(clientes) : ResponseEntity.notFound().build(); //404 Not Found
		return !clientes.isEmpty() ? ResponseEntity.ok(clientes) : ResponseEntity.noContent().build(); //204 No Content
	};*/
	
	@PostMapping
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalvo = clienteRepository.save(cliente);
		
		//publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	/*@GetMapping("/{codigo}")
	public Cliente buscarPeloCodigo(@PathVariable Long codigo) {
		return clienteRepository.findOne(codigo);
	}*/
	
	//Retornar 404 caso o cliente não exista
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Cliente cliente = clienteRepository.findOne(codigo);
		return !(cliente == null) ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build(); //404 Not Found
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		clienteRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long codigo, @Valid @RequestBody Cliente cliente){
		Cliente clienteSalva = clienteService.atualizar(codigo, cliente);
		
		return ResponseEntity.ok(clienteSalva);
	}
}
