package com.fernando.empleos.controller;

import com.fernando.empleos.helpers.Utileria;
import com.fernando.empleos.model.Categoria;
import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.services.ICategoriasService;
import com.fernando.empleos.services.IVacantesService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
@RequestMapping("/vacantes")
public class VacantesController {

  @Value("${empleosapp.ruta.imagenes}")
  private String ruta;
  @Autowired
  private IVacantesService serviceVacantes;

  @Autowired
  private ICategoriasService serviceCategorias;

  @GetMapping("/index")
  public String mostrarIndex(Model model) {
    // List<Vacante> vacantes = serviceVacante.buscarTodas();
    // model.addAttribute("vacantes", vacantes);
    List<Vacante> vacantes = serviceVacantes.buscarTodas();
    model.addAttribute("vacantes", vacantes);

    return "vacantes/listVacantes";
  }

  @GetMapping(value = "/indexPaginate")
    public String mostrarIndexPaginado(Model model, Pageable page) {
    Page<Vacante> lista = serviceVacantes.buscarTodas(page);
    model.addAttribute("vacantes", lista);
    return "vacantes/listVacantes";
  }

  @GetMapping("/create")
  public String crear(Vacante vacante, Model model) {
    // model.addAttribute("categorias", serviceCategorias.buscarTodas()); se define en el model attribute
    return "vacantes/formVacante";
  }

  @PostMapping("/save")
  public String guardar(
    Vacante vacante,
    BindingResult result,
    RedirectAttributes attributes,
    Model model,
    @RequestParam("archivoImagen") MultipartFile multiPart
  ) {
    if (result.hasErrors()) {
      for (ObjectError error : result.getAllErrors()) {
        System.out.println("Ocurrio un error: " + error.getDefaultMessage());
      }
      model.addAttribute("categorias", serviceCategorias.buscarTodas());
      return "vacantes/formVacante";
    }
    if (!multiPart.isEmpty()) {
      //String ruta = "/empleos/img-vacantes/"; // Linux/MAC
      // String ruta = "c:/empleos/img-vacantes/"; // Windows
      String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
      if (nombreImagen != null) { // La imagen si se subio
        // Procesamos la variable nombreImagen
        vacante.setImagen(nombreImagen);
      }
    }
    serviceVacantes.guardar(vacante);
    attributes.addFlashAttribute("msg", "Registro Guardado");
    System.out.println(vacante);
    return "redirect:/vacantes/index";
  }

  @PostMapping("/delete")
  public String eliminar(@RequestParam("id") int idVacante,RedirectAttributes attributes, Model model) {
    System.out.println("Borrando vacante con id: " + idVacante);
    serviceVacantes.eliminar(idVacante);
    attributes.addFlashAttribute("msg", "Registro elimado correctamente ");
    return "redirect:/vacantes/index";
  }

  @GetMapping("/edit/{id}")
  public String editar(@PathVariable("id") int idVacante, Model model) {
    Vacante vacante = serviceVacantes.buscarPorId(idVacante);
    // model.addAttribute("categorias", serviceCategorias.buscarTodas()); se define en el model attribute
    model.addAttribute("vacante", vacante);
    
    return "vacantes/formVacante";
  }

  @GetMapping("/view/{id}")
  public String verDetalle(@PathVariable("id") int idVacante, 
  Model model, Authentication auth) {
    String username = auth.getName(); 
    int bandera = 0; 
    for (GrantedAuthority rol : auth.getAuthorities()) {
      if (rol.getAuthority().equals("USUARIO")) {
        bandera = 1; 
      }
		}
    model.addAttribute("usuario", bandera);
    
    Vacante vacante = serviceVacantes.buscarPorId(idVacante);

    model.addAttribute("vacante", vacante);

    // Buscar los detalles de la vacante en la base de datos...

    return "vacantes/detalle";
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    webDataBinder.registerCustomEditor(
      Date.class,
      new CustomDateEditor(dateFormat, false)
    );
  }

  @ModelAttribute
  public void setGenericos(Model model) {
    model.addAttribute("categorias", serviceCategorias.buscarTodas());
  }
}
