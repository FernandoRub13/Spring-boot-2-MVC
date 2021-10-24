package com.fernando.empleos.services.db;

import java.util.List;

import com.fernando.empleos.model.Solicitud;
import com.fernando.empleos.repository.SolicitudesRepository;
import com.fernando.empleos.services.ISolicitudesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SolicitudesServiceJpa implements ISolicitudesService {

	@Autowired
	private SolicitudesRepository solicitudesRepository;

	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepository.save(solicitud);
		
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudesRepository.deleteById(idSolicitud);
		
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return solicitudesRepository.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		return  solicitudesRepository.getById(idSolicitud);
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return solicitudesRepository.findAll(page);
	}	

}

