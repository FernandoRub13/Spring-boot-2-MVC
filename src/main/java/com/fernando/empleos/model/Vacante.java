package com.fernando.empleos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Vacantes")
public class Vacante {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String nombre;
  private String descripcion;
  private Date fecha;
  private Double salario;
  private Integer destacado;
  private String imagen="no-image.svg";
  private String estatus;
  private String detalles;
  // CREATE TYPE estatus_type as ENUM ('Creada','Aprobada','Eliminada');
  @OneToOne
  @JoinColumn(name="idCategoria")
  private Categoria categoria;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  public Date getFecha() {
    return fecha;
  }
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  public Double getSalario() {
    return salario;
  }
  public void setSalario(Double salario) {
    this.salario = salario;
  }
  public Integer getDestacado() {
    return destacado;
  }
  public void setDestacado(Integer destacado) {
    this.destacado = destacado;
  }
  public String getImagen() {
    return imagen;
  }
  public void setImagen(String imagen) {
    this.imagen = imagen;
  }
  public void resetImagen() {
    this.imagen = null;
  }
  
  public String getEstatus() {
    return estatus;
  }
  public void setEstatus(String estatus) {
    this.estatus = estatus;
  }
  public String getDetalles() {
    return detalles;
  }
  public void setDetalles(String detalles) {
    this.detalles = detalles;
  }
  
  public Categoria getCategoria() {
    return categoria;
  }
  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }
  @Override
  public String toString() {
    return "Vacante [categoria=" + categoria + ", descripcion=" + descripcion + ", destacado=" + destacado
        + ", detalles=" + detalles + ", estatus=" + estatus + ", fecha=" + fecha + ", id=" + id + ", imagen=" + imagen
        + ", nombre=" + nombre + ", salario=" + salario + "]";
  }
  
  

  

}
