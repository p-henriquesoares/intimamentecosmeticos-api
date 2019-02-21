package com.intimamentecosmeticos.api.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Erro.class)
public abstract class Erro_ {

	public static volatile SingularAttribute<Erro, Long> codigo;
	public static volatile SingularAttribute<Erro, Date> data_ocorrencia;
	public static volatile SingularAttribute<Erro, String> origem;
	public static volatile SingularAttribute<Erro, String> descricao;

}

