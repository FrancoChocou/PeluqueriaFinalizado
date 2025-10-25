package com.idra.gestionpeluqueria.controller;

import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.service.TurnoService;
import com.idra.gestionpeluqueria.service.impl.TurnoServiceImpl;
import com.idra.gestionpeluqueria.dao.impl.TurnoDAOImpl;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador para la gestion de turnos de peluqueria.
 * Actua como intermediario entre la capa de presentacion y la capa de servicio,
 * coordinando las operaciones de creacion, consulta, actualizacion y gestion de turnos.
 * 
 * @author Idra
 */
public class TurnoController {
    
    private TurnoService turnoService;
    
    public TurnoController() {
        this.turnoService = new TurnoServiceImpl(new TurnoDAOImpl());
    }
    
    /**
     * Crea un nuevo turno en el sistema
     * @param turno El turno a crear
     * @throws ServiceException Si ocurre un error al crear el turno
     */
    public void crearTurno(Turno turno) throws ServiceException {
        turnoService.crearTurno(turno);
    }
    
    /**
     * Busca un turno por su ID
     * @param id El ID del turno a buscar
     * @return El turno encontrado o null si no existe
     * @throws ServiceException Si ocurre un error al buscar el turno
     */
    public Turno buscarTurnoPorId(int id) throws ServiceException {
        return turnoService.buscarTurnoPorId(id);
    }
    
    /**
     * Obtiene todos los turnos del sistema
     * @return Lista de todos los turnos
     * @throws ServiceException Si ocurre un error al obtener los turnos
     */
    public List<Turno> obtenerTodosTurnos() throws ServiceException {
        return turnoService.buscarTodosTurnos();
    }
    
    /**
     * Busca turnos por fecha específica
     * @param fecha La fecha para buscar turnos
     * @return Lista de turnos en la fecha especificada
     * @throws ServiceException Si ocurre un error al buscar los turnos
     */
    public List<Turno> buscarTurnosPorFecha(LocalDate fecha) throws ServiceException {
        return turnoService.buscarTurnosPorFecha(fecha);
    }
    
    /**
     * Busca turnos por cliente
     * @param clienteId El ID del cliente
     * @return Lista de turnos del cliente
     * @throws ServiceException Si ocurre un error al buscar los turnos
     */
    public List<Turno> buscarTurnosPorCliente(int clienteId) throws ServiceException {
        return turnoService.buscarTurnosPorCliente(clienteId);
    }
    
    /**
     * Busca turnos por estado
     * @param estado El estado del turno a buscar
     * @return Lista de turnos con el estado especificado
     * @throws ServiceException Si ocurre un error al buscar los turnos
     */
    public List<Turno> buscarTurnosPorEstado(String estado) throws ServiceException {
        return turnoService.buscarTurnosPorEstado(estado);
    }
    
    /**
     * Actualiza la información de un turno existente
     * @param turno El turno con la información actualizada
     * @throws ServiceException Si ocurre un error al actualizar el turno
     */
    public void actualizarTurno(Turno turno) throws ServiceException {
        turnoService.actualizarTurno(turno);
    }
    
    /**
     * Cancela un turno existente
     * @param id El ID del turno a cancelar
     * @throws ServiceException Si ocurre un error al cancelar el turno
     */
    public void cancelarTurno(int id) throws ServiceException {
        turnoService.cancelarTurno(id);
    }
    
    /**
     * Marca un turno como completado
     * @param id El ID del turno a completar
     * @throws ServiceException Si ocurre un error al completar el turno
     */
    public void completarTurno(int id) throws ServiceException {
        turnoService.completarTurno(id);
    }
    
    /**
     * Valida la disponibilidad de un turno
     * @param turno El turno a validar
     * @return true si hay disponibilidad, false en caso contrario
     * @throws ServiceException Si ocurre un error durante la validación
     */
    public boolean validarDisponibilidad(Turno turno) throws ServiceException {
        return turnoService.validarDisponibilidad(turno);
    }
    
    /**
     * Calcula el total pagado en el día actual
     * @return El monto total pagado hoy
     * @throws ServiceException Si ocurre un error al calcular el total
     */
    public double calcularTotalPagadoHoy() throws ServiceException {
        return turnoService.calcularTotalPagadoHoy();
    }
}