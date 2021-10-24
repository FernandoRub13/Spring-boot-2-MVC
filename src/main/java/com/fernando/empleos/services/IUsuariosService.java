package com.fernando.empleos.services;
import java.util.List;
import com.fernando.empleos.model.Usuario;

public interface IUsuariosService {

	/** Implementar método para registrar un usuario nuevo. 
	 * 	1. Usar la plantilla del archivo formRegistro.html
	 * 	2. El método para mostrar el formulario para registrar y el método para guardar el usuario deberá 
	 * 	   estar en el Controlador HomeController.
	 * 	3. Al guardar el usuario se le asignará el perfil USUARIO y la fecha de Registro
	 * 	   sera la fecha actual del sistema.
	 * @param usuario
	 */
	void guardar(Usuario usuario);
	
	// Método que elimina un usuario de la base de datos.
	void eliminar(Integer idUsuario);
	
	// Implementar método que recupera todos los usuarios. Usar vista de listUsuarios.html
	List<Usuario> buscarTodos();

	Usuario buscarPorUsername(String username);
}

// Agregar al archivo menu.html el link para acceder al listado de Usuarios y configurar el link del botón Registrarse

