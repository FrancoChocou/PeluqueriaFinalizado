package com.idra.gestionpeluqueria.service;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.util.List;
/**
 * Interfaz que define los servicios de negocio para la gestion de servicios de peluqueria.
 * Proporciona metodos para realizar operaciones CRUD, validaciones y 
 * consultas sobre servicios ofrecidos por la peluqueria.
 * 
 * @author Idra
 */
public interface ServicioService {
    /**
     * Crea un nuevo servicio en el sistema.
     * 
     * @param servicio El servicio a crear
     * @throws ServiceException Si ocurre un error al crear el servicio
     */
    void crearServicio(Servicio servicio) throws ServiceException;
    /**
     * Busca un servicio por su identificador único.
     * 
     * @param id El ID del servicio a buscar
     * @return El servicio encontrado o null si no existe
     * @throws ServiceException Si ocurre un error al buscar el servicio
     */
    Servicio buscarServicioPorId(int id) throws ServiceException;
    /**
     * Obtiene todos los servicios registrados en el sistema.
     * 
     * @return Lista de todos los servicios
     * @throws ServiceException Si ocurre un error al obtener los servicios
     */
    List<Servicio> buscarTodosServicios() throws ServiceException;
     /**
     * Obtiene únicamente los servicios que están activos.
     * 
     * @return Lista de servicios activos
     * @throws ServiceException Si ocurre un error al obtener los servicios
     */
    List<Servicio> buscarServiciosActivos() throws ServiceException;
    /**
     * Busca servicios que coincidan con un tipo específico.
     * 
     * @param tipoServicio El tipo de servicio a buscar
     * @return Lista de servicios del tipo especificado
     * @throws ServiceException Si ocurre un error al buscar los servicios
     */
    List<Servicio> buscarServiciosPorTipo(String tipoServicio) throws ServiceException;
    /**
     * Actualiza la información de un servicio existente.
     * 
     * @param servicio El servicio con la información actualizada
     * @throws ServiceException Si ocurre un error al actualizar el servicio
     */
    void actualizarServicio(Servicio servicio) throws ServiceException;
    /**
     * Elimina un servicio del sistema.
     * 
     * @param id El ID del servicio a eliminar
     * @throws ServiceException Si ocurre un error al eliminar el servicio
     */
    void eliminarServicio(int id) throws ServiceException;
     /**
     * Valida que los datos de un servicio cumplan con las reglas de negocio.
     * 
     * @param servicio El servicio a validar
     * @return true si los datos son válidos, false en caso contrario
     * @throws ServiceException Si ocurre un error durante la validación
     */
    boolean validarServicio(Servicio servicio) throws ServiceException;
}