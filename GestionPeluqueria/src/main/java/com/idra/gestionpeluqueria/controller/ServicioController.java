package com.idra.gestionpeluqueria.controller;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.service.ServicioService;
import com.idra.gestionpeluqueria.service.impl.ServicioServiceImpl;
import com.idra.gestionpeluqueria.dao.impl.ServicioDAOImpl;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.util.List;

/**
 * Controlador para la gestion de servicios de peluqueria.
 * Actua como intermediario entre la capa de presentacion y la capa de servicio,
 * delegando las operaciones CRUD y validaciones al ServicioService.
 * 
 * @author Idra
 */

public class ServicioController {
    
    private ServicioService servicioService;
    /**
     * Constructor que inicializa el controlador con sus dependencias.
     * Crea una instancia de ServicioServiceImpl con su DAO correspondiente.
     */
    
    public ServicioController() {
        this.servicioService = new ServicioServiceImpl(new ServicioDAOImpl());
    }
    
    /**
     * Crea un nuevo servicio en el sistema.
     * 
     * @param servicio El servicio a crear 
     * @throws ServiceException Si ocurre un error al crear el servicio
     */
    public void crearServicio(Servicio servicio) throws ServiceException {
        servicioService.crearServicio(servicio);
    }
    
    /**
     * Busca un servicio por su identificador unico.
     * 
     * @param id El ID del servicio a buscar 
     * @return El servicio encontrado o null si no existe
     * @throws ServiceException Si ocurre un error al buscar el servicio
     */
    public Servicio buscarServicioPorId(int id) throws ServiceException {
        return servicioService.buscarServicioPorId(id);
    }
    
    /**
     * Obtiene la lista completa de todos los servicios registrados 
     * 
     * @return Lista de todos los servicios en el sistema 
     * @throws ServiceException Si ocurre un error al obtener los servicios 
     */
    public List<Servicio> obtenerTodosServicios() throws ServiceException {
        return servicioService.buscarTodosServicios();
    }
    
    /**
     * Obtiene unicamente los servicios que estan activos en el sistema.
     * 
     * @return Lista de servicios activos 
     * @throws ServiceException Si ocurre un error al obtener los servicios 
     */
    public List<Servicio> obtenerServiciosActivos() throws ServiceException {
        return servicioService.buscarServiciosActivos();
    }
   
    /**
     * Busca servicios que coincidan con un tipo especifico 
     * 
     * @param tipoServicio El tipo de servicio a buscar 
     * @return Lista de servicios que coinciden con el tipo especificado 
     * @throws ServiceException Si ocurre un error al buscar los servicios 
     */
    public List<Servicio> buscarServiciosPorTipo(String tipoServicio) throws ServiceException {
        return servicioService.buscarServiciosPorTipo(tipoServicio);
    }
    
    /**
     * Actualiza la informacion de un servicio existente.
     * 
     * @param servicio El servicio con la informacion actualizada 
     * @throws ServiceException Si ocurre un error al actualizar el servicio 
     */
    public void actualizarServicio(Servicio servicio) throws ServiceException {
        servicioService.actualizarServicio(servicio);
    }
    
    /**
     * Elimina un servicio del sistema 
     * 
     * @param id El ID del servicio a eliminar 
     * @throws ServiceException Si ocurre un error al eliminar el servicio 
     */
    public void eliminarServicio(int id) throws ServiceException {
        servicioService.eliminarServicio(id);
    }
    
    /**
     * Valida si los datos de un servicio son correctos y cumplen las reglas de negocio.
     * 
     * @param servicio El servicio a validar 
     * @return true si los datos son validos, false en caso contrario 
     * @throws ServiceException Si ocurre un error durante la validacion 
     */
    public boolean validarServicio(Servicio servicio) throws ServiceException {
        return servicioService.validarServicio(servicio);
    }
}