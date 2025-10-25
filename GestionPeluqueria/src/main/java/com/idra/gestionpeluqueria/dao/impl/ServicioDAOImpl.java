package com.idra.gestionpeluqueria.dao.impl;

import com.idra.gestionpeluqueria.dao.ServicioDAO;
import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.enums.TipoServicio;
import com.idra.gestionpeluqueria.config.DatabaseConfig;
import com.idra.gestionpeluqueria.exception.DAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion de la interfaz ServicioDAO para acceso a datos de servicios.
 * Gestiona todas las operaciones de persistencia relacionadas con servicios
 * de peluqueria en la base de datos MySQL.
 * 
 * @author Idra
 */
public class ServicioDAOImpl implements ServicioDAO {
    
    private Connection getConnection() throws SQLException {
        return DatabaseConfig.getInstance().getConnection();
    }
    
    @Override
    public void crear(Servicio servicio) throws DAOException {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio, duracion_minutos, tipo_servicio, activo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, servicio.getNombre());
            stmt.setString(2, servicio.getDescripcion());
            stmt.setDouble(3, servicio.getPrecio());
            stmt.setInt(4, servicio.getDuracionMinutos());
            stmt.setString(5, servicio.getTipoServicio().name());
            stmt.setBoolean(6, servicio.isActivo());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al crear servicio, ninguna fila afectada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    servicio.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Error al obtener ID generado para el servicio.");
                }
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al crear servicio en la base de datos", e);
        }
    }
    
    @Override
    public Servicio buscarPorId(int id) throws DAOException {
        String sql = "SELECT * FROM servicios WHERE id = ?";
        Servicio servicio = null;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                servicio = mapResultSetToServicio(rs);
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar servicio por ID: " + id, e);
        }
        
        return servicio;
    }
    
    @Override
public List<Servicio> buscarTodos() throws DAOException {
    String sql = "SELECT * FROM servicios ORDER BY nombre";
    List<Servicio> servicios = new ArrayList<>();
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        System.out.println("🔍 DAO - Buscando TODOS los servicios..."); // DEBUG
        
        int count = 0;
        while (rs.next()) {
            servicios.add(mapResultSetToServicio(rs));
            count++;
        }
        
        System.out.println("✅ DAO - Encontrados " + count + " servicios"); // DEBUG
        
    } catch (SQLException e) {
        throw new DAOException("Error al buscar todos los servicios", e);
    }
    
    return servicios;
}
    
    @Override
    public List<Servicio> buscarActivos() throws DAOException {
        String sql = "SELECT * FROM servicios WHERE activo = true ORDER BY nombre";
        List<Servicio> servicios = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                servicios.add(mapResultSetToServicio(rs));
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar servicios activos", e);
        }
        
        return servicios;
    }
    
    @Override
    public List<Servicio> buscarPorTipo(String tipoServicio) throws DAOException {
        String sql = "SELECT * FROM servicios WHERE tipo_servicio = ? AND activo = true ORDER BY nombre";
        List<Servicio> servicios = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tipoServicio);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                servicios.add(mapResultSetToServicio(rs));
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al buscar servicios por tipo: " + tipoServicio, e);
        }
        
        return servicios;
    }
    
    @Override
public void actualizar(Servicio servicio) throws DAOException {
    String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, precio = ?, duracion_minutos = ?, tipo_servicio = ?, activo = ? WHERE id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        System.out.println("🔄 DAO - Actualizando servicio ID: " + servicio.getId()); // DEBUG
        
        stmt.setString(1, servicio.getNombre());
        stmt.setString(2, servicio.getDescripcion());
        stmt.setDouble(3, servicio.getPrecio());
        stmt.setInt(4, servicio.getDuracionMinutos());
        stmt.setString(5, servicio.getTipoServicio().name());
        stmt.setBoolean(6, servicio.isActivo());
        stmt.setInt(7, servicio.getId());
        
        int affectedRows = stmt.executeUpdate();
        System.out.println("✅ DAO - Filas afectadas: " + affectedRows); // DEBUG
        
        if (affectedRows == 0) {
            throw new DAOException("Error al actualizar servicio, ninguna fila afectada.");
        }
        
    } catch (SQLException e) {
        System.err.println("❌ DAO - Error SQL: " + e.getMessage()); // DEBUG
        e.printStackTrace(); // DEBUG
        throw new DAOException("Error al actualizar servicio con ID: " + servicio.getId(), e);
    }
}
    
    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM servicios WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException("Error al eliminar servicio, ninguna fila afectada.");
            }
            
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar servicio con ID: " + id, e);
        }
    }
    
    private Servicio mapResultSetToServicio(ResultSet rs) throws SQLException {
        Servicio servicio = new Servicio();
        servicio.setId(rs.getInt("id"));
        servicio.setNombre(rs.getString("nombre"));
        servicio.setDescripcion(rs.getString("descripcion"));
        servicio.setPrecio(rs.getDouble("precio"));
        servicio.setDuracionMinutos(rs.getInt("duracion_minutos"));
        servicio.setTipoServicio(TipoServicio.valueOf(rs.getString("tipo_servicio")));
        servicio.setActivo(rs.getBoolean("activo"));
        return servicio;
    }
}