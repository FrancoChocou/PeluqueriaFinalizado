package com.idra.gestionpeluqueria.dao;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad Servicio.
 * Proporciona metodos para gestionar los servicios de peluqueria en la base de datos,
 * incluyendo operaciones CRUD y consultas especializadas.
 * 
 * @author Franco
 */
public interface ServicioDAO {
    /**
     * Crea un nuevo servicio en la base de datos.
     * 
     * @param servicio El servicio a crear 
     * @throws DAOException Si ocurre un error al crear el servicio 
     */
    void crear(Servicio servicio) throws DAOException;
    
    /**
     * Busca un servicio por su identificador unico.
     * 
     * @param id El ID del servicio a buscar 
     * @return El servicio encontrado o null si no existe 
     * @throws DAOException Si ocurre un error al buscar el servicio 
     */
    Servicio buscarPorId(int id) throws DAOException;
    
    /**
     * Obtiene todos los servicios registrados en el sistema.
     * 
     * @return Lista de todos los servicios 
     * @throws DAOException Si ocurre un error al obtener los servicios 
     */
    List<Servicio> buscarTodos() throws DAOException;
    /**
     * Obtiene unicamente los servicios que estan marcados como activos.
     * 
     * @return Lista de servicios activos 
     * @throws DAOException Si ocurre un error al obtener los servicios 
     */
    List<Servicio> buscarActivos() throws DAOException;
    
    /**
     * Busca servicios que coincidan con un tipo especifico 
     * 
     * @param tipoServicio El tipo de servicio a buscar 
     * @return Lista de servicios del tipo especificado 
     * @throws DAOException Si ocurre un error al buscar los servicios 
     */
    List<Servicio> buscarPorTipo(String tipoServicio) throws DAOException;
    /**
     * Actualiza los datos de un servicio existente.
     * 
     * @param servicio El servicio con los datos actualizados 
     * @throws DAOException Si ocurre un error al actualizar el servicio 
     */
    void actualizar(Servicio servicio) throws DAOException;
    
    /**
     * Elimina un servicio de la base de datos 
     * 
     * @param id El ID del servicio a eliminar 
     * @throws DAOException Si ocurre un error al eliminar el servicio
     */
    void eliminar(int id) throws DAOException;
}