package com.fernando.empleos.services.db;

import java.util.List;
import java.util.Optional;

import com.fernando.empleos.model.Categoria;
import com.fernando.empleos.repository.CategoriasRepository;
import com.fernando.empleos.services.ICategoriasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CategoriasServiceJpa implements ICategoriasService {

  @Autowired
  private CategoriasRepository categoriasRepository;

  @Override
  public void guardar(Categoria categoria) {
    categoriasRepository.save(categoria);
    
  }

  @Override
  public List<Categoria> buscarTodas() {
    return categoriasRepository.findAll();
  }

  @Override
  public Categoria buscarPorId(Integer idCategoria) {
    Optional<Categoria> categoria = categoriasRepository.findById(idCategoria);
    if (categoria.isPresent()) {
      return categoria.get();
    }
    return null; 
  }

  @Override
  public void eliminar(Integer idCategoria) {
    categoriasRepository.deleteById(idCategoria);
  }

  @Override
  public Page<Categoria> buscarTodas(Pageable page) {
    return categoriasRepository.findAll(page);
  }

}
