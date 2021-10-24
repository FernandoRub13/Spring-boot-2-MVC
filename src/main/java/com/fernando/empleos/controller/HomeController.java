package com.fernando.empleos.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fernando.empleos.model.Perfil;
import com.fernando.empleos.model.Usuario;
import com.fernando.empleos.model.Vacante;
import com.fernando.empleos.services.ICategoriasService;
import com.fernando.empleos.services.IUsuariosService;
import com.fernando.empleos.services.IVacantesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

	@Autowired
	private IVacantesService serviceVacante;

	@Autowired
	private IUsuariosService serviceUsuario;

	@Autowired
	private ICategoriasService serviceCategoria;

	@Autowired
	private PasswordEncoder passwordEncoder;

/*
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
	*/

  @GetMapping("/")
  public String mostrarHome(Model model) {
    // model.addAttribute("vacantes", serviceVacante.buscarDestacadas()); se define en el model attribute
    // model.addAttribute("categorias", serviceCategoria.buscarTodas()); se define en el model attribute
    return "home";
  }
 
	@GetMapping("/index")
	public String mostrarIndex(Authentication auth, HttpSession session, Model model) {
		String username = auth.getName(); 
		for (GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("ROL: "+ rol.getAuthority());
		}

		if (session.getAttribute("usuario") == null) {
			Usuario usuario = serviceUsuario.buscarPorUsername(username);
			usuario.setPassword(null);
			System.out.println("Usuario: " + usuario);
			session.setAttribute("usuario", usuario);
		}

		System.out.println("Nombre de usuario: " + username);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String mostrarLogin() {
		return "formLogin";
	}

	@GetMapping("/logout")
		public String logout(HttpServletRequest request, RedirectAttributes attributes ){
		SecurityContextLogoutHandler logoutHandler =  new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		attributes.addFlashAttribute("logout", "Has cerrado sesión exitosamente");
		return "redirect:/login";
	}
	
	@GetMapping("/signup")	
  public String registrarse(Usuario usuario) {
		
		return "usuarios/formUsuario";
	}
	
	@PostMapping("/signup")		
	public String registrarse(Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		usuario.setPassword(pwdEncriptado);
		if (result.hasErrors()) {
			return "usuarios/formUsuario";
		}
		usuario.setFechaRegistro(new Date());
		usuario.setEstatus(1);
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3); // Perfil USUARIO
		serviceUsuario.guardar(usuario);
		attributes.addFlashAttribute("msg", "Usuario creado con éxito");	
		return "redirect:/usuarios/index";
		
  
	}
 
	@GetMapping("/search")	
	public String buscar(@ModelAttribute("vacanteSearch") Vacante vacante, Model model) {
		System.out.println("Buscando por: " + vacante );

		ExampleMatcher matcher = ExampleMatcher
		.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacante.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		return "home";
	}

	@GetMapping("/bcrypt/{texto}")	
	@ResponseBody
	public String encriptar(@PathVariable String texto) {
		return texto + " Encriptado en Bcrypt: " + passwordEncoder.encode(texto);
	
	}

	//initBinder para String, si los detecta vacios en el DAta Binding los settea a NULL
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute
  public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.resetImagen();
		model.addAttribute("vacantesDestacadas", serviceVacante.buscarDestacadas());
		model.addAttribute("vacanteSearch", vacanteSearch);
		model.addAttribute("categorias", serviceCategoria.buscarTodas());

  }
  
}
