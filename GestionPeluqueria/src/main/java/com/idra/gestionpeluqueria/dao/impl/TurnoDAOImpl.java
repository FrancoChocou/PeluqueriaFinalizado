package com.idra.gestionpeluqueria.dao.impl;

import com.idra.gestionpeluqueria.dao.TurnoDAO;
import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.model.Cliente;
import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.enums.EstadoTurno;
import com.idra.gestionpeluqueria.model.enums.EstadoPago;
import com.idra.gestionpeluqueria.model.enums.FormaPago;
import com.idra.gestionpeluqueria.config.DatabaseConfig;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de la interfaz TurnoDAO para acceso a datos de turnos.
 * Gestiona todas las operaciones de persistencia relacionadas con turnos 
 * en la base de datos MySQL, incluyendo consultas complejas con joins 
 * a las tablas de clientes y servicios.
 * 
 * @author Idra
 */
public class TurnoDAOImpl implements TurnoDAO {
    
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }
    
    @Override
    public void crear(Turno turno) throws DAOException {
        String sql = "INSERT INTO turnos (cliente_id, servicio_id, fecha_hora, notas, estado, estado_pago, forma_pago, monto_pagado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, turno.getCliente().getId());
            stmt.setInt(2, turno.getServicio().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(turno.getFechaHora()));
            stmt.setString(4, turno.getNotas());
            stmt.setString(5, turno.getEstado().name());
            stmt.setString(6, turno.getEstadoPago().name());
            stmt.setString(7, turno.getFormaPago() != null ? turno.getFormaPago().name() : null);
            stmt.setDouble(8, turno.getMontoPagado());
            stmt.setTimestamp(9, Timestamp.valueOf(turno.getFechaCreacion()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al crear turno, ninguna fila afectada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    turno.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Error al obtener ID generado para el turno.");
                }
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al crear turno en la base de datos", e);
        }
    }
    
    @Override
    public Turno buscarPorId(int id) throws DAOException {
        String sql = "SELECT t.*, c.nombre as cliente_nombre, c.apellido as cliente_apellido, c.telefono as cliente_telefono, " +
                    "s.nombre as servicio_nombre, s.precio as servicio_precio, s.duracion_minutos as servicio_duracion " +
                    "FROM turnos t " +
                    "INNER JOIN clientes c ON t.cliente_id = c.id " +
                    "INNER JOIN servicios s ON t.servicio_id = s.id " +
                    "WHERE t.id = ?";
        Turno turno = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                turno = mapResultSetToTurno(rs);
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar turno por ID: " + id, e);
        }
        
        return turno;
    }
    
    @Override
    public List<Turno> buscarTodos() throws DAOException {
        String sql = "SELECT t.*, c.nombre as cliente_nombre, c.apellido as cliente_apellido, c.telefono as cliente_telefono, " +
                    "s.nombre as servicio_nombre, s.precio as servicio_precio, s.duracion_minutos as servicio_duracion " +
                    "FROM turnos t " +
                    "INNER JOIN clientes c ON t.cliente_id = c.id " +
                    "INNER JOIN servicios s ON t.servicio_id = s.id " +
                    "ORDER BY t.fecha_hora DESC";
        List<Turno> turnos = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                turnos.add(mapResultSetToTurno(rs));
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar todos los turnos", e);
        }
        
        return turnos;
    }
    
    @Override
    public List<Turno> buscarPorFecha(LocalDate fecha) throws DAOException {
        String sql = "SELECT t.*, c.nombre as cliente_nombre, c.apellido as cliente_apellido, c.telefono as cliente_telefono, " +
                    "s.nombre as servicio_nombre, s.precio as servicio_precio, s.duracion_minutos as servicio_duracion " +
                    "FROM turnos t " +
                    "INNER JOIN clientes c ON t.cliente_id = c.id " +
                    "INNER JOIN servicios s ON t.servicio_id = s.id " +
                    "WHERE DATE(t.fecha_hora) = ? " +
                    "ORDER BY t.fecha_hora";
        List<Turno> turnos = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                turnos.add(mapResultSetToTurno(rs));
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar turnos por fecha: " + fecha, e);
        }
        
        return turnos;
    }
    
    @Override
    public List<Turno> buscarPorCliente(int clienteId) throws DAOException {
        String sql = "SELECT t.*, c.nombre as cliente_nombre, c.apellido as cliente_apellido, c.telefono as cliente_telefono, " +
                    "s.nombre as servicio_nombre, s.precio as servicio_precio, s.duracion_minutos as servicio_duracion " +
                    "FROM turnos t " +
                    "INNER JOIN clientes c ON t.cliente_id = c.id " +
                    "INNER JOIN servicios s ON t.servicio_id = s.id " +
                    "WHERE t.cliente_id = ? " +
                    "ORDER BY t.fecha_hora DESC";
        List<Turno> turnos = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                turnos.add(mapResultSetToTurno(rs));
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar turnos por cliente ID: " + clienteId, e);
        }
        
        return turnos;
    }
    
    @Override
    public List<Turno> buscarPorEstado(String estado) throws DAOException {
        String sql = "SELECT t.*, c.nombre as cliente_nombre, c.apellido as cliente_apellido, c.telefono as cliente_telefono, " +
                    "s.nombre as servicio_nombre, s.precio as servicio_precio, s.duracion_minutos as servicio_duracion " +
                    "FROM turnos t " +
                    "INNER JOIN clientes c ON t.cliente_id = c.id " +
                    "INNER JOIN servicios s ON t.servicio_id = s.id " +
                    "WHERE t.estado = ? " +
                    "ORDER BY t.fecha_hora DESC";
        List<Turno> turnos = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                turnos.add(mapResultSetToTurno(rs));
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar turnos por estado: " + estado, e);
        }
        
        return turnos;
    }
    
   @Override
public void actualizar(Turno turno) throws DAOException {
    String sql = "UPDATE turnos SET cliente_id = ?, servicio_id = ?, fecha_hora = ?, notas = ?, estado = ?, estado_pago = ?, forma_pago = ?, monto_pagado = ? WHERE id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, turno.getCliente().getId());
        stmt.setInt(2, turno.getServicio().getId());
        stmt.setTimestamp(3, Timestamp.valueOf(turno.getFechaHora()));
        stmt.setString(4, turno.getNotas());
        stmt.setString(5, turno.getEstado().name());
        stmt.setString(6, turno.getEstadoPago().name());
        stmt.setString(7, turno.getFormaPago() != null ? turno.getFormaPago().name() : null);
        stmt.setDouble(8, turno.getMontoPagado());
        stmt.setInt(9, turno.getId());
        
        int affectedRows = stmt.executeUpdate();
        
        if (affectedRows == 0) {
            throw new DAOException("Error al actualizar turno, ninguna fila afectada.");
        }
        
    } catch (SQLException e) {
        throw new DAOException("Error al actualizar turno con ID: " + turno.getId(), e);
    }
}
    
    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM turnos WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al eliminar turno, ninguna fila afectada.");
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar turno con ID: " + id, e);
        }
    }
    
    @Override
    public boolean existeTurnoEnFechaHora(int servicioId, LocalDateTime fechaHora) throws DAOException {
        String sql = "SELECT COUNT(*) FROM turnos WHERE servicio_id = ? AND fecha_hora = ? AND estado != 'CANCELADO'";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, servicioId);
            stmt.setTimestamp(2, Timestamp.valueOf(fechaHora));
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al verificar disponibilidad de turno", e);
        }
        
        return false;
    }
    
    private Turno mapResultSetToTurno(ResultSet rs) throws SQLException {
        Turno turno = new Turno();
        turno.setId(rs.getInt("id"));
        
        // Crear y configurar Cliente
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNombre(rs.getString("cliente_nombre"));
        cliente.setApellido(rs.getString("cliente_apellido"));
        cliente.setTelefono(rs.getString("cliente_telefono"));
        turno.setCliente(cliente);
        
        // Crear y configurar Servicio
        Servicio servicio = new Servicio();
        servicio.setId(rs.getInt("servicio_id"));
        servicio.setNombre(rs.getString("servicio_nombre"));
        servicio.setPrecio(rs.getDouble("servicio_precio"));
        servicio.setDuracionMinutos(rs.getInt("servicio_duracion"));
        turno.setServicio(servicio);
        
        turno.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
        turno.setNotas(rs.getString("notas"));
        turno.setEstado(EstadoTurno.valueOf(rs.getString("estado")));
        turno.setEstadoPago(EstadoPago.valueOf(rs.getString("estado_pago")));
        
        String formaPagoStr = rs.getString("forma_pago");
        if (formaPagoStr != null) {
            turno.setFormaPago(FormaPago.valueOf(formaPagoStr));
        }
        
        turno.setMontoPagado(rs.getDouble("monto_pagado"));
        turno.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        
        return turno;
    }
}