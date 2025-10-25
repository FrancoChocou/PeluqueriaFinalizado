package com.idra.gestionpeluqueria.model;

import com.idra.gestionpeluqueria.model.enums.TipoServicio;
/**
 * Clase que representa un servicio ofrecido por la peluqueria.
 * Contiene informacion sobre el tipo de servicio, precio, duracion 
 * y estado de disponibilidad.
 * 
 * @author Idra 
 */
public class Servicio {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
    private TipoServicio tipoServicio;
    private boolean activo;
    
    /**
     * Constructor por defecto sin parametros 
     */
    
    public Servicio() {}
    /**
     * Constructor con parametros principales del servicio.
     * El servicio se marca como activo por defecto.
     * 
     * @param nombre Nombre del servicio 
     * @param descripcion Descripcion detallada del servicio
     * @param precio Precio del servicio 
     * @param duracionMinutos Duracion estimada del servicio en minutos 
     * @param tipoServicio Tipo de servicio (enum TipoServicio)
     */
    public Servicio(String nombre, String descripcion, double precio, int duracionMinutos, TipoServicio tipoServicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
        this.tipoServicio = tipoServicio;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    
    public TipoServicio getTipoServicio() { return tipoServicio; }
    public void setTipoServicio(TipoServicio tipoServicio) { this.tipoServicio = tipoServicio; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
    /**
     * Retorna una representacion en texto del servicio.
     * 
     * @return String con nombre,precio y duracion del servicio
     */
    @Override
    public String toString() {
        return nombre + " - $" + precio + " (" + duracionMinutos + " min)";
    }
}