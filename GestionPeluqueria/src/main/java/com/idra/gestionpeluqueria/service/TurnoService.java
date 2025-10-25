package com.idra.gestionpeluqueria.service;

import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.time.LocalDate;
import java.util.List;
/**
 * Interfaz que define los servicios de negocio para la gestion de turnos.
 * Proporciona metodos para realizar operaciones CRUD, validaciones,
 * consultas y gestion del estado de los turnos de la peluqueria.
 * @author Idra
 */
public interface TurnoService {
    /**
     * Crea un nuevo turno en el sistema.
     * 
     * @param turno El turno a crear
     * @throws ServiceException Si ocurre un error al crear el turno
     */
    void crearTurno(Turno turno) throws ServiceException;
    /**
     * Busca un turno por su identificador único.
     * 
     * @param id El ID del turno a buscar
     * @return El turno encontrado o null si no existe
     * @throws ServiceException Si ocurre un error al buscar el turno
     */
    Turno buscarTurnoPorId(int id) throws ServiceException;
    /**
     * Obtiene todos los turnos registrados en el sistema.
     * 
     * @return Lista de todos los turnos
     * @throws ServiceException Si ocurre un error al obtener los turnos
     */
    List<Turno> buscarTodosTurnos() throws ServiceException;
    /**
     * Busca todos los turnos agendados para una fecha específica.
     * 
     * @param fecha La fecha para buscar turnos
     * @return Lista de turnos en la fecha especificada
     * @throws ServiceException Si ocurre un error al buscar los turnos
     */
    List<Turno> buscarTurnosPorFecha(LocalDate fecha) throws ServiceException;
     /**
     * Busca todos los turnos asociados a un cliente específico.
     * 
     * @param clienteId El ID del cliente
     * @return Lista de turnos del cliente
     * @throws ServiceException Si ocurre un error al buscar los turnos
     */
    List<Turno> buscarTurnosPorCliente(int clienteId) throws ServiceException;
     /**
     * Busca turnos que tengan un estado específico.
     * 
     * @param estado El estado del turno a buscar
     * @return Lista de turnos con el estado especificado
     * @throws ServiceException Si ocurre un error al buscar los turnos
     */
    List<Turno> buscarTurnosPorEstado(String estado) throws ServiceException;
    /**
     * Actualiza la información de un turno existente.
     * 
     * @param turno El turno con la información actualizada
     * @throws ServiceException Si ocurre un error al actualizar el turno
     */
    void actualizarTurno(Turno turno) throws ServiceException;
    /**
     * Cancela un turno existente cambiando su estado a CANCELADO.
     * 
     * @param id El ID del turno a cancelar
     * @throws ServiceException Si ocurre un error al cancelar el turno
     */
    void cancelarTurno(int id) throws ServiceException;
     /**
     * Marca un turno como completado y actualiza el estado de pago.
     * 
     * @param id El ID del turno a completar
     * @throws ServiceException Si ocurre un error al completar el turno
     */
    void completarTurno(int id) throws ServiceException;
    /**
     * Valida la disponibilidad de un turno verificando que no existan conflictos de horario.
     * 
     * @param turno El turno a validar
     * @return true si hay disponibilidad, false en caso contrario
     * @throws ServiceException Si ocurre un error durante la validación
     */
    boolean validarDisponibilidad(Turno turno) throws ServiceException;
     /**
     * Calcula el monto total pagado en el día actual.
     * 
     * @return El monto total pagado en el día de hoy
     * @throws ServiceException Si ocurre un error al calcular el total
     */
    double calcularTotalPagadoHoy() throws ServiceException;
}