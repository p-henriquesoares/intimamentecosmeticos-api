package com.intimamentecosmeticos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intimamentecosmeticos.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
