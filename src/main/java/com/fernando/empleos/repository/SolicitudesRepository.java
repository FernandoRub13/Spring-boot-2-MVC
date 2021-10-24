package com.fernando.empleos.repository;

import com.fernando.empleos.model.Solicitud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
  