package com.fernando.empleos.services.db;

import java.util.List;
import java.util.Optional;

import com.fernando.empleos.model.Usuario;
import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.repository.UsuariosRepository;
import com.fernando.empleos.services.IUsuariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService {

  @Autowired
  private UsuariosRepository usuariosRepository;

  @Override
  public void guardar(Usuario usuario) {
    usuariosRepository.save(usuario);
  
  }

  @Override
  public void eliminar(Integer idUsuario) {
    usuariosRepository.deleteById(idUsuario);

  }

  @Override
  public List<Usuario> buscarTodos() {
    return usuariosRepository.findAll();
  
  }
  
  
}
