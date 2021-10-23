package com.fernando.empleos.controller;

import com.fernando.empleos.services.IUsuariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private IUsuariosService usuariosService;

    
    @GetMapping("/index")
	public String mostrarIndex(Model model) {
		model.addAttribute("usuarios", usuariosService.buscarTodos());
    	
    	return "usuarios/listUsuarios";
	}
    
    @PostMapping("/delete")
	public String eliminar(@RequestParam("id") int idUsuario, RedirectAttributes attributes) {		    	
		usuariosService.eliminar(idUsuario);
    	attributes.addFlashAttribute("msg", "Usuario eliminado con exito");
		return "redirect:/usuarios/index";
	}
}
