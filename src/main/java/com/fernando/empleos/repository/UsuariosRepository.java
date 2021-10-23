package com.fernando.empleos.repository;

import com.fernando.empleos.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
  
}
