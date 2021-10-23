package com.fernando.empleos.repository;

import com.fernando.empleos.model.Perfil;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilesRepository  extends JpaRepository<Perfil, Integer> {
  
}
