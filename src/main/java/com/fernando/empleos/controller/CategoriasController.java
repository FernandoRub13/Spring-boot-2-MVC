package com.fernando.empleos.controller;


import java.util.List;

import com.fernando.empleos.model.Categoria;
import com.fernando.empleos.services.ICategoriasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

  @Autowired 
  private ICategoriasService serviceCategorias;

  // @RequestMapping(value="/index", method = RequestMethod.GET)
  @GetMapping("/index")
  public String mostrarIndex(Model model){
    List<Categoria> categorias = serviceCategorias.buscarTodas();
  	model.addAttribute("categorias", categorias);
    return "categorias/listCategorias";
  }
  // @RequestMapping(value="/create", method = RequestMethod.GET)
  @GetMapping("/create")
  public String crear(Categoria categoria){
    return "categorias/formCategoria";
  }
  // @RequestMapping(value="/save", method = RequestMethod.POST)
  @PostMapping("/save")
  public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes){
    if (result.hasErrors()) {
      for (ObjectError error: result.getAllErrors()){
        System.out.println("Ocurrio un error: " + error.getDefaultMessage());
        }
      return "categorias/formCategoria";
    }
    serviceCategorias.guardar(categoria);
    attributes.addFlashAttribute("msg", "Registro Guardado");
    System.out.println(categoria);
    return "redirect:/categorias/index";
  }
  
}
