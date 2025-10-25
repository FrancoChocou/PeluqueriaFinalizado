package com.idra.gestionpeluqueria.dao;

import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad Turno.
 * Proporciona metodos para gestionar los turnos de la peluqueria, incluyendo
 * operaciones CRUD, consultas por diferentes criterios y validaciones de disponibilidad.
 * 
 * @author Idra
 */
public interface TurnoDAO {
    /**
     * Crea un nuevo turno en la base de datos.
     * 
     * @param turno El turno a crear 
     * @throws DAOException Si ocurre un error al crear el turno 
     */
    void crear(Turno turno) throws DAOException;
    
    /**
     * Busca un turno por su identificador unico
     * 
     * @param id El ID del turno a buscar 
     * @return El turno encontrado o null si no existe 
     * @throws DAOException Si ocurre un error al buscar el turno 
     */
    Turno buscarPorId(int id) throws DAOException;
    
    /**
     * Obtiene todos los turnos registrados en el sistema.
     * 
     * @return Lista de todos los turnos 
     * @throws DAOException Si ocurre un error al obtener los turnos 
     */
    
    List<Turno> buscarTodos() throws DAOException;
    /**
     * Busca todos los turnos agendados para una fecha especifica 
     * 
     * @param fecha La fecha para buscar turnos 
     * @return Lista de turnos en la fecha especificada 
     * @throws DAOException Si ocurre un error al buscar los turnos 
     */
    List<Turno> buscarPorFecha(LocalDate fecha) throws DAOException;
    
    /**
     * Busca todos los turnos asociados a un cliente especifico  
     * 
     * @param clienteId El ID del cliente 
     * @return Lista de turnos del cliente 
     * @throws DAOException Si ocurre un error al buscar los turnos 
     */
    List<Turno> buscarPorCliente(int clienteId) throws DAOException;
    
    /**
     * Busca turnos que tengan un estado especifico
     * 
     * @param estado El estado del turno a buscar 
     * @return Lista de turnos con el estado especifico 
     * @throws DAOException Si ocurre un error al buscar los turnos 
     */
    List<Turno> buscarPorEstado(String estado) throws DAOException;
    /**
     * Actualiza los datos de un turno existente 
     * 
     * @param turno El turno con los datos actualizados 
     * @throws DAOException Si ocurre un error al actualizar el turno 
     */
    void actualizar(Turno turno) throws DAOException;
    /**
     * Elimina un turno de la base de datos.
     * 
     * @param id El ID del turno a eliminar 
     * @throws DAOException Si ocurre un error al eliminar el turno 
     */
    void eliminar(int id) throws DAOException;
    
    /**
     * Verifica si ya existe un turno para un servicio en una fecha y hora especifica.
     * Util para evitar conflicos de horarios.
     * 
     * @param servicioId El ID del servicio 
     * @param fechaHora La fecha y hora a verificar 
     * @return true si ya existe un turno, false en caso contrario 
     * @throws DAOException Si ocurre un error al verificar la disponibilidad
     */
    boolean existeTurnoEnFechaHora(int servicioId, java.time.LocalDateTime fechaHora) throws DAOException;
}