package com.fernando.empleos.controller;

import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.services.IVacantesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

  @Autowired
  private IVacantesService serviceVacante;
  
  @GetMapping("/delete")
  public String eliminar(@RequestParam("id") int idVacante, Model model){
    System.out.println("Borrando vacante con id: " + idVacante);
    model.addAttribute("id", idVacante);
    return "mensaje";
  }
  @GetMapping("/view/{id}")
  public String verDetalle(@PathVariable("id") int idVacante, Model model){

    Vacante vacante = serviceVacante.buscarPorId(idVacante);

    System.out.println("idVacante: " + idVacante);
    model.addAttribute("vacante", vacante);
    System.out.println("Vacante"+vacante);

    // Buscar los detalles de la vacante en la base de datos...

    return "vacantes/detalle";
  }
}
