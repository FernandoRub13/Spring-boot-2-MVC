package com.fernando.empleos.services.db;

import java.util.List;
import java.util.Optional;

import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.repository.VacantesRepository;
import com.fernando.empleos.services.IVacantesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
public class VacantesServiceJpa implements IVacantesService {

  @Autowired
  private VacantesRepository vacantesRepository;

  @Override
  public List<Vacante> buscarTodas() {
    return  vacantesRepository.findAll();
  }

  @Override
  public Vacante buscarPorId(Integer idVacante) {
    Optional<Vacante> optional = vacantesRepository.findById(idVacante);
    if (optional.isPresent()) {
      return optional.get();
    }
    return null;
  }

  @Override
  public void guardar(Vacante vacante) {
    vacantesRepository.save(vacante);
  }

  @Override
  public List<Vacante> buscarDestacadas() {
    return vacantesRepository.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
  }

  @Override
  public void eliminar(Integer idVacante) {
    vacantesRepository.deleteById(idVacante);
  
  }

  @Override
  public List<Vacante> buscarByExample(Example<Vacante> example) {
    return vacantesRepository.findAll(example);
  }

  @Override
  public Page<Vacante> buscarTodas(Pageable pageable) {
    return vacantesRepository.findAll(pageable);
  }
  
}
