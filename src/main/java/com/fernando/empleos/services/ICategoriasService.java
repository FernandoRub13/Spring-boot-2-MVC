package com.fernando.empleos.services;

import java.util.List;

import com.fernando.empleos.model.Categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoriasService {
	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);	
	void eliminar(Integer idCategoria);
	Page<Categoria> buscarTodas(Pageable pageable);
}