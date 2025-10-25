package com.idra.gestionpeluqueria.model;

import java.time.LocalDate;
/**
 * Clase que representa a un cliente de la peluqueria.
 * Contiene la informacion personal y de contacto del cliente,
 * asi como la fecha de registro en el sistema.
 * @author Idra
 */
public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private LocalDate fechaRegistro;
    
    /**
     * Constructor por defecto sin parametros.
     */
    public Cliente() {}
    /**
     * Constructor con parametros principales del cliente.
     * Inicializa la fecha de registro con la fecha actual.
     * 
     * @param nombre Nombre del cliente 
     * @param apellido Apellido del cliente 
     * @param telefono Numero de telefono del cliente 
     * @param email Direccion de correo electronico del cliente 
     */
    public Cliente(String nombre, String apellido, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = LocalDate.now();
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    /**
     * Retorna una representacion en texto del cliente 
     * 
     * @return String con nombre completo y telefono del cliente 
     */
    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + telefono;
    }
}