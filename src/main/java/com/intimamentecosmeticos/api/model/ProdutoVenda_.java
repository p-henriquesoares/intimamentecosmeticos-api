package com.intimamentecosmeticos.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProdutoVenda.class)
public abstract class ProdutoVenda_ {

	public static volatile SingularAttribute<ProdutoVenda, Long> codigo;
	public static volatile SingularAttribute<ProdutoVenda, Venda> venda;
	public static volatile SingularAttribute<ProdutoVenda, Produto> produto;

}

