package com.fernando.empleos.services;

import java.util.LinkedList;
import java.util.List;

import com.fernando.empleos.model.Categoria; 
import org.springframework.stereotype.Service;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

  private List<Categoria> lista = null;

  

  public CategoriasServiceImpl() {
    lista = new LinkedList<Categoria>();
    			// Creamos la oferta de Trabajo 1.
			Categoria categoria1= new Categoria();
			categoria1.setId(1);
			categoria1.setNombre("Ingeniero Civil"); // Titulo de la vacante
			categoria1.setDescripcion("Solicitamos Ing. Civil para diseñar puente peatonal.");
						
			// Creamos la oferta de Trabajo 2.
			Categoria categoria2 = new Categoria();
			categoria2.setId(2);
			categoria2.setNombre("Contador Publico");
			categoria2.setDescripcion("Empresa importante solicita contador con 5 años de experiencia titulado.");
		
			// Creamos la oferta de Trabajo 3.
			Categoria categoria3 = new Categoria();
			categoria3.setId(3);
			categoria3.setNombre("Ingeniero Eléctrico");
			categoria3.setDescripcion("Empresa internacional solicita Ingeniero mecánico para mantenimiento de la instalación eléctrica.");		
		
			// Creamos la oferta de Trabajo 3.
			Categoria categoria4 = new Categoria();
			categoria4.setId(4);
			categoria4.setNombre("Desarrollo de software");
			categoria4.setDescripcion("Trabajo para programadores");		
			
			/**
			 * Agregamos los 4 objetos de tipo Categoria a la lista ...
			 */
			lista.add(categoria1);			
			lista.add(categoria2);
			lista.add(categoria3);
			lista.add(categoria4);
  }

  @Override
  public void guardar(Categoria categoria) {
    lista.add(categoria);
    
  }

  @Override
  public List<Categoria> buscarTodas() {
    return lista;
  }

  @Override
  public Categoria buscarPorId(Integer idCategoria) {
    for (Categoria categoria : lista) {
      if (categoria.getId()==idCategoria) {
        return categoria;
      }
    }
    return null;
  }

	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}
  
}
