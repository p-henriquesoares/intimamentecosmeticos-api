package com.intimamentecosmeticos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intimamentecosmeticos.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
