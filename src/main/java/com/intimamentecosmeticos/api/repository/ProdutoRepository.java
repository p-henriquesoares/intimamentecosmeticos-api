package com.intimamentecosmeticos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intimamentecosmeticos.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
