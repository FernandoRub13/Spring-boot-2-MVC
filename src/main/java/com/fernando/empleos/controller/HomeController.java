package com.fernando.empleos.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.services.IVacantesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@Autowired
	private IVacantesService serviceVacante;

  @GetMapping("/tabla")
  public String mostrarTabla(Model model){
    List<Vacante> lista = serviceVacante.buscarTodas();
    model.addAttribute("vacantes", lista);

    return "tabla";
  }

  @GetMapping("/detalle")
  public String mostrarDetalle(Model model){
    Vacante vacante = new Vacante();
    vacante.setNombre("Ingeniero de comunicaciones");
    vacante.setDescripcion("Se solicita ingeniero para soporte a intranet");
    vacante.setFecha(new Date());
    vacante.setSalario(9700.0);
    model.addAttribute("vacante", vacante);

    return "detalle";

  }

  @GetMapping("/listado")
  public String mostrarListado(Model model) {
    List<String> lista = new LinkedList<>();
    lista.add("Ingeniero de sistemas");
    lista.add("Vendedor");
    lista.add("Auxiliar en sistemas");
    lista.add("Arquitecto");

    model.addAttribute("empleos", lista);

    return "listado";
  }

  @GetMapping("/")
  public String mostrarHome(Model model) {
    List<Vacante> lista = serviceVacante.buscarTodas();
    model.addAttribute("vacantes", lista);

    return "home";
  }
  
  
}
