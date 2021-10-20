package com.fernando.empleos.services;

import java.util.List;

import com.fernando.empleos.model.Vacante;

public interface IVacantesService {
  List<Vacante> buscarTodas(); 
  Vacante buscarPorId(Integer idVacante);
  void guardar(Vacante vacante);
}
