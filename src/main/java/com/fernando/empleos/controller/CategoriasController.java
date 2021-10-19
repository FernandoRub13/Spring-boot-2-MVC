package com.fernando.empleos.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {

  // @GetMapping("/index")
  @RequestMapping(value="/index", method = RequestMethod.GET)
  public String mostrarIndex(Model model){
    return "categorias/listCategoria";
  }
  // @GetMapping("/create")
  @RequestMapping(value="/create", method = RequestMethod.GET)
  public String crear(){
    return "categorias/formCategoria";
  }
  // @PostMapping("/save")
  @RequestMapping(value="/save", method = RequestMethod.POST)
  public String crear(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, Model model){
    System.out.println(nombre + descripcion);
    return "categorias/listCategorias";
  }
}
