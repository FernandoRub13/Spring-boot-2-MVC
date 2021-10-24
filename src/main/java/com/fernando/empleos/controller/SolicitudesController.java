package com.fernando.empleos.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.fernando.empleos.helpers.Utileria;
import com.fernando.empleos.model.Categoria;
import com.fernando.empleos.model.Solicitud;
import com.fernando.empleos.model.Usuario;
import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.services.ICategoriasService;
import com.fernando.empleos.services.ISolicitudesService;
import com.fernando.empleos.services.IUsuariosService;
import com.fernando.empleos.services.IVacantesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

	@Autowired
	private ISolicitudesService serviceSolicitudes;

	@Autowired
	private IVacantesService serviceVacantes;
	
	/**
	 * El valor sera el directorio en donde se guardarán 
	 * los archivos de los Curriculums Vitaes de los usuarios.
	 *  (Declarada en application properties)
	 */
	@Value("${empleosapp.ruta.cv}")
	private String ruta;
		
    /**
	 * Metodo que muestra la lista de solicitudes sin paginacion
	 * Seguridad: Solo disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
    @GetMapping("/index") 
	public String mostrarIndex(Model model) {
		model.addAttribute("solicitudes", serviceSolicitudes.buscarTodas());
		return "solicitudes/listSolicitudes";
		
	}
    
    /**
	 * Metodo que muestra la lista de solicitudes con paginacion
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * @return
	 */
	@GetMapping("/indexPaginate")
		public String mostrarIndexPaginado(Model model, Pageable page) {
			Page<Solicitud> lista = serviceSolicitudes.buscarTodas(page);
			model.addAttribute("solicitudes", lista);
		return "solicitudes/listSolicitudes";
		
	}
    
	/**
	 * Método para renderizar el formulario para aplicar para una Vacante
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@GetMapping("/create/{idVacante}")
	public String crear( @PathVariable("idVacante") int idVacante, 
	Solicitud solicitud, 
	// Authentication auth,
	//  HttpSession session,
	  Model model ) {

		// String username = auth.getName();

		// if (session.getAttribute("usuario") == null) {
		// 	Usuario usuario = serviceUsuario.buscarPorUsername(username);
		// 	usuario.setPassword(null);
		// 	System.out.println("Usuario: " + usuario);
		// 	session.setAttribute("usuario", usuario);
		// }

		Vacante vacante = serviceVacantes.buscarPorId(idVacante); 
		model.addAttribute("vacante", vacante);
		return "solicitudes/formSolicitud";
	}
	
	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * @return
	 */
	@PostMapping("/save")
	public String guardar(
		Solicitud solicitud,
    BindingResult result,
    RedirectAttributes attributes,
    Model model,
		@RequestParam("archivoCV") MultipartFile multiPart
	) {	
		if (result.hasErrors()) {
      for (ObjectError error : result.getAllErrors()) {
        System.out.println("Ocurrio un error: " + error.getDefaultMessage());
      }
      
      return "solicitudes/formSolicitud";
    }
    if (!multiPart.isEmpty()) {
      //String ruta = "/empleos/cv/"; // Linux/MAC
      // String ruta = "c:/empleos/cv/"; // Windows
      String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
      if (nombreArchivo != null) { // La imagen si se subio
        // Procesamos la variable nombreArchivo
        solicitud.setArchivo(nombreArchivo);
      }
    }
		solicitud.setFecha(new Date());
		serviceSolicitudes.guardar(solicitud);
    attributes.addFlashAttribute("msg", "¡Solicitud Enviada!");
    System.out.println(solicitud);
		return "redirect:/";	
		
	}
	
	/**
	 * Método para eliminar una solicitud
	 * Seguridad: Solo disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR. 
	 * @return
	 */
	@PostMapping("/delete")
	public String eliminar( @RequestParam("id") int id, RedirectAttributes attributes ) {
		serviceSolicitudes.eliminar(id);
		attributes.addFlashAttribute("msg", "¡Solicitud Eliminada!");

		return "redirect:/solicitudes/index";
		
	}
			
	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * @param webDataBinder
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
	}

}
