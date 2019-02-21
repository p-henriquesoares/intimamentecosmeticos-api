package com.intimamentecosmeticos.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.intimamentecosmeticos.api.model.Erro;
import com.intimamentecosmeticos.api.resources.ErroResource;

/*Classe responsável por capturar exceções de respostas de entidades*/
/*ControllerAdvice pois observa toda a aplicação*/
@ControllerAdvice
public class IntimamenteCosmeticosExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ErroResource erroResource;
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		String mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		//Novo modo de pegar as exceptions utilizando a biblioteca 'commons-lang3'
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex)+" - "+request.toString();
		
		erroResource.criar(new Erro(new Date(), this.getClass().getSimpleName(), mensagemDesenvolvedor));
		
		List<MensagemErro> mensagensErros = Arrays.asList(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, mensagensErros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request); 
	}
	
	//Trata a exceção ao deletar, caso não encontre o recurso o 404 será lançado
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString()+" - "+request.toString();
		
		erroResource.criar(new Erro(new Date(), this.getClass().getSimpleName(), mensagemDesenvolvedor));
		
		List<MensagemErro> mensagensErros = Arrays.asList(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, mensagensErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request); 
	}
	
	/*Loga requisições com campos nulos*/
	/*handleMethodArgumentNotValid CTRL+ESPAÇO*/
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		erroResource.criar(new Erro(new Date(), this.getClass().getSimpleName(), ex.toString()+" - "+request.toString()));
		List<MensagemErro> mensagensErros = criarListaMensagemErro(ex.getBindingResult());
		
		return handleExceptionInternal(ex, mensagensErros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/*Loga requisições inválidas*/
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("requisicao_invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString()+" - "+request.toString();
		
		erroResource.criar(new Erro(new Date(), this.getClass().getSimpleName(), mensagemDesenvolvedor));
		
		List<MensagemErro> mensagensErros = Arrays.asList(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, mensagensErros, headers, HttpStatus.BAD_REQUEST, request);
	}

	/*Mensagens que 'não consegui ler' de retorno do POST ou seja, a mensagem padrão quando não implementamos essa classe ao definir o atributo 'spring.jackson.deserialization.fail-on-unknown-properties=true' no arquivo application.properties*/
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("mensagem_invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		
		erroResource.criar(new Erro(new Date(), this.getClass().getSimpleName(), mensagemDesenvolvedor));
		
		List<MensagemErro> mensagensErros = Arrays.asList(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, mensagensErros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private List<MensagemErro> criarListaMensagemErro(BindingResult bindingResult){
		List<MensagemErro> mensagensErros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			mensagensErros.add(new MensagemErro(mensagemUsuario, mensagemDesenvolvedor));
		}
		
		return mensagensErros;
	}
	
	public static class MensagemErro {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public MensagemErro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
			this.mensagemUsuario = mensagemUsuario;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}
}
