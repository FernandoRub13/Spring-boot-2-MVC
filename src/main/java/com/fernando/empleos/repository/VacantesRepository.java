package com.fernando.empleos.repository;

import java.util.List;

import com.fernando.empleos.model.Vacante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {
  List<Vacante> findByEstatus(String estatus);  
  List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer  destacado, String estatus);  
  List<Vacante> findByEstatusOrderByIdDesc(String estatus);
  // Make a method to find by salary between a range
  List<Vacante> findBySalarioBetweenOrderBySalarioDesc(Double salarioMin, Double salarioMax);
  // Make a method to find by estatus in an arrays of strings
  List<Vacante> findByEstatusIn(String[] estatus);




}
