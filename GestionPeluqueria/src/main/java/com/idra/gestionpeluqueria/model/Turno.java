package com.idra.gestionpeluqueria.model;

import com.idra.gestionpeluqueria.model.enums.EstadoTurno;
import com.idra.gestionpeluqueria.model.enums.EstadoPago;
import com.idra.gestionpeluqueria.model.enums.FormaPago;
import java.time.LocalDateTime;
/**
 * Clase que representa un turno o cita en la peluqueria.
 * Relaciona a un cliente con un servicio en una fecha y hora especifica,
 * e incluye informacion sobre el estado del turno y el pago.
 * 
 * @author Idra
 */
public class Turno {
    private int id;
    private Cliente cliente;
    private Servicio servicio;
    private LocalDateTime fechaHora;
    private String notas;
    private EstadoTurno estado;
    private EstadoPago estadoPago;
    private FormaPago formaPago;
    private double montoPagado;
    private LocalDateTime fechaCreacion;
    
    /**
     * Constructores por defecto sin parametros 
     */
    public Turno() {}
    
    /**
     * Constructor con parametros principales del turno.
     * Inicializa el estado como CONFIRMADO, estado de pago como PENDIENTE,
     * monto pagado en 0 y fecha de creacion con la fecha actual.
     * 
     * @param cliente CLiente que solicito el turno 
     * @param servicio Servicio a realizar en el turno 
     * @param fechaHora Fecha y hora programada para el turno 
     */
    public Turno(Cliente cliente, Servicio servicio, LocalDateTime fechaHora) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
        this.estado = EstadoTurno.CONFIRMADO;
        this.estadoPago = EstadoPago.PENDIENTE;
        this.montoPagado = 0.0;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    public EstadoTurno getEstado() { return estado; }
    public void setEstado(EstadoTurno estado) { this.estado = estado; }
    
    public EstadoPago getEstadoPago() { return estadoPago; }
    public void setEstadoPago(EstadoPago estadoPago) { this.estadoPago = estadoPago; }
    
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    
    public double getMontoPagado() { return montoPagado; }
    public void setMontoPagado(double montoPagado) { this.montoPagado = montoPagado; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    /**
     * Calcula el saldo pendiente de pago del turno 
     * 
     * @return Diferencia entre el precio del servicio y el monto ya pagado
     */
    public double getSaldoPendiente() {
        return servicio.getPrecio() - montoPagado;
    }
    /**
     * Retorna una representacion en texto del turno.
     * 
     * @return String con cliente, servicio y fecha/hora del turno 
     */
    @Override
    public String toString() {
        return cliente.getNombre() + " " + cliente.getApellido() + " - " + 
               servicio.getNombre() + " - " + fechaHora;
    }
}