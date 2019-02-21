package com.intimamentecosmeticos.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produto.class)
public abstract class Produto_ {

	public static volatile SingularAttribute<Produto, Marca> marca;
	public static volatile SingularAttribute<Produto, Long> codigo;
	public static volatile SingularAttribute<Produto, Cor> cor;
	public static volatile SingularAttribute<Produto, Categoria> categoria;
	public static volatile SingularAttribute<Produto, String> cod_barra;
	public static volatile SingularAttribute<Produto, String> descricao;

}

