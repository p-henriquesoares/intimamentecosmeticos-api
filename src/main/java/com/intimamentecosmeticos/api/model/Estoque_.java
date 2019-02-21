package com.intimamentecosmeticos.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Estoque.class)
public abstract class Estoque_ {

	public static volatile SingularAttribute<Estoque, Long> codigo;
	public static volatile SingularAttribute<Estoque, Produto> produto;
	public static volatile SingularAttribute<Estoque, Integer> quantidade;

}

