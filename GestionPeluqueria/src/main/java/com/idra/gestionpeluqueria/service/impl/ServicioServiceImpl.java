package com.idra.gestionpeluqueria.service.impl;

import com.idra.gestionpeluqueria.dao.ServicioDAO;
import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.service.ServicioService;
import com.idra.gestionpeluqueria.exception.ServiceException;
import com.idra.gestionpeluqueria.exception.ValidacionException;
import java.util.List;
import com.idra.gestionpeluqueria.dao.ServicioDAO;
import com.idra.gestionpeluqueria.exception.DAOException;
/**
 * Implementacion de la interfaz ServicioService.
 * Gestiona la logica de negocio relacionada con servicios de peluqueria,
 * incluyendo validaciones y coordinacion con la capa de acceso a datos.
 * 
 * @author Idra
 */
public class ServicioServiceImpl implements ServicioService {
    
    private ServicioDAO servicioDAO;
    /**
     * Constructor que inicializa el servicio con su DAO correspondiente.
     * 
     * @param servicioDAO El DAO para operaciones de persistencia de servicios
     */
    public ServicioServiceImpl(ServicioDAO servicioDAO) {
        this.servicioDAO = servicioDAO;
    }
    
    @Override
    public void crearServicio(Servicio servicio) throws ServiceException {
        try {
            if (!validarServicio(servicio)) {
                throw new ValidacionException("Datos del servicio no válidos");
            }
            servicioDAO.crear(servicio);
        } catch (Exception e) {
            throw new ServiceException("Error al crear servicio: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Servicio buscarServicioPorId(int id) throws ServiceException {
        try {
            return servicioDAO.buscarPorId(id);
        } catch (Exception e) {
            throw new ServiceException("Error al buscar servicio por ID: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Servicio> buscarTodosServicios() throws ServiceException {
        try {
            return servicioDAO.buscarTodos();
        } catch (Exception e) {
            throw new ServiceException("Error al buscar todos los servicios: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Servicio> buscarServiciosActivos() throws ServiceException {
        try {
            return servicioDAO.buscarActivos();
        } catch (Exception e) {
            throw new ServiceException("Error al buscar servicios activos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Servicio> buscarServiciosPorTipo(String tipoServicio) throws ServiceException {
        try {
            return servicioDAO.buscarPorTipo(tipoServicio);
        } catch (Exception e) {
            throw new ServiceException("Error al buscar servicios por tipo: " + e.getMessage(), e);
        }
    }
    
    @Override
public void actualizarServicio(Servicio servicio) throws ServiceException {
    try {
        System.out.println("🔄 Service - Actualizando servicio: " + servicio.getId()); // DEBUG
        servicioDAO.actualizar(servicio);
        System.out.println("✅ Service - Servicio actualizado correctamente"); // DEBUG
    } catch (DAOException e) {
        System.err.println("❌ Service - Error: " + e.getMessage()); // DEBUG
        throw new ServiceException("Error al actualizar servicio: " + e.getMessage(), e);
    }
}
    
    @Override
    public void eliminarServicio(int id) throws ServiceException {
        try {
            servicioDAO.eliminar(id);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar servicio: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean validarServicio(Servicio servicio) throws ServiceException {
        if (servicio == null) return false;
        if (servicio.getNombre() == null || servicio.getNombre().trim().isEmpty()) return false;
        if (servicio.getPrecio() <= 0) return false;
        if (servicio.getDuracionMinutos() <= 0) return false;
        if (servicio.getTipoServicio() == null) return false;
        
        return true;
    }
}