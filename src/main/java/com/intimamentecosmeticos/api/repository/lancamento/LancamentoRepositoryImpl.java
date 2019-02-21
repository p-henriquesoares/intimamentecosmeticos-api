package com.intimamentecosmeticos.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.intimamentecosmeticos.api.model.Lancamento;
import com.intimamentecosmeticos.api.model.Lancamento_;
import com.intimamentecosmeticos.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//Criar restrições
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);		
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);	
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
				
		return manager.createQuery(criteria).getSingleResult(); //Count retornará apenas 1 resultado
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			/*Configurei no POM.xml a dependência abaixo de modo a baixar o JAR e em seguida removi a dependência:
			 * 
			 * <dependency>
			 *  <groupId>org.hibernate</groupId>
			 *  <artifactId>hibernate-jpamodelgen</artifactId>
			 *  <version>5.0.12.Final</version>
			 *  <scope>provided</scope>
			 *  </dependency>
			 *  
			 *  Após baixar o JAR, cliquei com o botão direito no projeto > properties > Java Compiler > 
			 *  Annotation Processing > Marcar *Enable project specific settings* e preencher o campo Generated source directory com o conteúdo 'src/main/java'.
			 *  Factory Path > *Enable project specific settings* > Desmarcar o único plugin existente de nome 'org.eclipse.jst.ws.annotations.core' e importar o JAR baixado de nome 'hibernate-jpamodelgen-5.0.12.Final.jar'
			 *  */
			
			/*Não é obrigatório realizar os passos acima, os mesmos foram feitos de modo com que não trabalhássemos com string no trecho de código abaixo, evitando assim erros:
			 * 
			 * builder.lower(root.get("descricao"))
			 * 
			 * Quanto utiliza-se o modo abaixo, o código fica mais consistente:
			 * 
			 * builder.lower(root.get(Lancamento_.descricao))
			 * 
			 * https://hibernate.org/orm/tooling/
			 * */
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if(lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));		
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
