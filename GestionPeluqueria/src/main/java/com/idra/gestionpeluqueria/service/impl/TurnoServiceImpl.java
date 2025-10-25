package com.idra.gestionpeluqueria.service.impl;

import com.idra.gestionpeluqueria.dao.TurnoDAO;
import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.service.TurnoService;
import com.idra.gestionpeluqueria.exception.ServiceException;
import com.idra.gestionpeluqueria.exception.ValidacionException;
import com.idra.gestionpeluqueria.exception.DAOException;
import com.idra.gestionpeluqueria.model.enums.EstadoPago;
import com.idra.gestionpeluqueria.model.enums.EstadoTurno;
import java.time.LocalDate;
import java.util.List;
/**
 * Implementacion de la interfaz TurnoService.
 * Gestiona la logica de negocio relacionada con turnos, incluyendo
 * validaciones de disponibilidad,cambios de estado y calculos financieros.
 * 
 * @author Franco
 */
public class TurnoServiceImpl implements TurnoService {
    
    private TurnoDAO turnoDAO;
    /**
     * Constructor que inicializa el servicio con su DAO correspondiente.
     * 
     * @param turnoDAO El DAO para operaciones de persistencia de turnos
     */
    public TurnoServiceImpl(TurnoDAO turnoDAO) {
        this.turnoDAO = turnoDAO;
    }
    
    @Override
    public void crearTurno(Turno turno) throws ServiceException {
        try {
            if (!validarDisponibilidad(turno)) {
                throw new ValidacionException("No hay disponibilidad para ese horario");
            }
            turnoDAO.crear(turno);
        } catch (DAOException | ValidacionException e) {
            throw new ServiceException("Error al crear turno: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Turno buscarTurnoPorId(int id) throws ServiceException {
        try {
            return turnoDAO.buscarPorId(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar turno por ID: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Turno> buscarTodosTurnos() throws ServiceException {
        try {
            return turnoDAO.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar todos los turnos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Turno> buscarTurnosPorFecha(LocalDate fecha) throws ServiceException {
        try {
            return turnoDAO.buscarPorFecha(fecha);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar turnos por fecha: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Turno> buscarTurnosPorCliente(int clienteId) throws ServiceException {
        try {
            return turnoDAO.buscarPorCliente(clienteId);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar turnos por cliente: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Turno> buscarTurnosPorEstado(String estado) throws ServiceException {
        try {
            return turnoDAO.buscarPorEstado(estado);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar turnos por estado: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void actualizarTurno(Turno turno) throws ServiceException {
        try {
            turnoDAO.actualizar(turno);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar turno: " + e.getMessage(), e);
        }
    }
    
   @Override
public void completarTurno(int id) throws ServiceException {
    try {
        Turno turno = turnoDAO.buscarPorId(id);
        if (turno != null) {
            turno.setEstado(EstadoTurno.COMPLETADO);
            turno.setEstadoPago(EstadoPago.PAGADO);
            turno.setMontoPagado(turno.getServicio().getPrecio());
            turnoDAO.actualizar(turno);
        } else {
            throw new ServiceException("Turno no encontrado con ID: " + id);
        }
    } catch (DAOException e) {
        throw new ServiceException("Error al completar turno: " + e.getMessage(), e);
    }
}

@Override
public void cancelarTurno(int id) throws ServiceException {
    try {
        Turno turno = turnoDAO.buscarPorId(id);
        if (turno != null) {
            turno.setEstado(EstadoTurno.CANCELADO);
            turnoDAO.actualizar(turno);
        } else {
            throw new ServiceException("Turno no encontrado con ID: " + id);
        }
    } catch (DAOException e) {
        throw new ServiceException("Error al cancelar turno: " + e.getMessage(), e);
    }
}
    @Override
    public boolean validarDisponibilidad(Turno turno) throws ServiceException {
        try {
            return !turnoDAO.existeTurnoEnFechaHora(
                turno.getServicio().getId(), 
                turno.getFechaHora()
            );
        } catch (DAOException e) {
            throw new ServiceException("Error al validar disponibilidad: " + e.getMessage(), e);
        }
    }
    
    @Override
    public double calcularTotalPagadoHoy() throws ServiceException {
        try {
            List<Turno> turnosHoy = turnoDAO.buscarPorFecha(LocalDate.now());
            return turnosHoy.stream()
                .filter(turno -> turno.getEstadoPago() == EstadoPago.PAGADO)
                .mapToDouble(turno -> turno.getMontoPagado())
                .sum();
        } catch (DAOException e) {
            throw new ServiceException("Error al calcular total pagado hoy: " + e.getMessage(), e);
        }
    }
}