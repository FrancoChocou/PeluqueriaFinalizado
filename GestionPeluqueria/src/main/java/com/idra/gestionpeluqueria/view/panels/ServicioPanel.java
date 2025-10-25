package com.idra.gestionpeluqueria.view.panels;
import com.idra.gestionpeluqueria.controller.ServicioController;
import com.idra.gestionpeluqueria.exception.ServiceException;
import java.util.List;
import com.idra.gestionpeluqueria.model.Servicio;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.enums.TipoServicio;
import com.idra.gestionpeluqueria.view.dialogs.ServicioDialog;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/**
 * Panel para la gestion de servicios de la peluqueria.
 * Proporciona una interfaz para visualizar, agregar, editar, eliminar y buscar servicios.
 * Incluye funcionalidades de filtrado por tipo de servicio y cambio de estado activo/inactivo.
 * 
 * @author Idra
 */
public class ServicioPanel extends JPanel {
    private JTable tablaServicios;
    private DefaultTableModel tableModel;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActivarDesactivar, btnBuscar;
    private JTextField txtBuscar;
    private JComboBox<String> comboFiltroTipo;
    
    /**
     * Constructor que inicializa el panel de servicios y sus componentes.
     */
    public ServicioPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        createHeaderPanel();
        createTablePanel();
        createToolbar();
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Gestión de Servicios");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));

        JLabel subtitleLabel = new JLabel("Administre los servicios y precios de la peluquería");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 240, 240));
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void createToolbar() {
        JPanel toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        toolbarPanel.setBackground(new Color(240, 240, 240));
        toolbarPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnAgregar = createToolbarButton("➕ Agregar Servicio", new Color(39, 174, 96));
        btnEditar = createToolbarButton("✏️ Editar", new Color(41, 128, 185));
        btnEliminar = createToolbarButton("🗑️ Eliminar", new Color(231, 76, 60));
        btnActivarDesactivar = createToolbarButton("⚡ Estado", new Color(243, 156, 18));

        btnAgregar.addActionListener(e -> abrirDialogoServicio(null));
        btnEditar.addActionListener(e -> editarServicioSeleccionado());
        btnEliminar.addActionListener(e -> eliminarServicioSeleccionado());
        btnActivarDesactivar.addActionListener(e -> cambiarEstadoServicio());

        // Filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterPanel.setBackground(new Color(240, 240, 240));

        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        comboFiltroTipo = new JComboBox<>(new String[]{"Todos", "CORTE", "TINTURA", "PEINADO", "ALISADO", "MECHAS", "BARBA", "CEJAS", "TRATAMIENTO"});
        comboFiltroTipo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        comboFiltroTipo.addActionListener(e -> filtrarPorTipo());

        // Búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setBackground(new Color(240, 240, 240));

        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        txtBuscar = new JTextField(15);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        btnBuscar = createToolbarButton("🔍 Buscar", new Color(155, 89, 182));
        btnBuscar.addActionListener(e -> buscarServicios());

        searchPanel.add(lblBuscar, BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        searchPanel.add(btnBuscar, BorderLayout.EAST);

        filterPanel.add(lblFiltro);
        filterPanel.add(comboFiltroTipo);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(searchPanel);

        toolbarPanel.add(btnAgregar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnEditar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnEliminar);
        toolbarPanel.add(Box.createHorizontalStrut(10));
        toolbarPanel.add(btnActivarDesactivar);
        toolbarPanel.add(Box.createHorizontalStrut(30));
        toolbarPanel.add(filterPanel);

        add(toolbarPanel, BorderLayout.SOUTH);
    }

    private JButton createToolbarButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void createTablePanel() {
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Duración", "Tipo", "Estado"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaServicios = new JTable(tableModel);
        tablaServicios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaServicios.setRowHeight(35);
        tablaServicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaServicios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaServicios.getTableHeader().setBackground(new Color(70, 130, 180));
        tablaServicios.getTableHeader().setForeground(Color.WHITE);

        tablaServicios.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String estado = value.toString();
                    if (estado.equals("Activo")) {
                        c.setBackground(new Color(220, 255, 220));
                        c.setForeground(new Color(0, 128, 0));
                    } else {
                        c.setBackground(new Color(255, 220, 220));
                        c.setForeground(new Color(128, 0, 0));
                    }
                    if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                        c.setForeground(table.getSelectionForeground());
                    }
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        add(scrollPane, BorderLayout.CENTER);
    }

  private void abrirDialogoServicio(Object[] datosServicio) {
    Servicio servicio = null;
    String titulo = "Agregar Servicio";
    
    if (datosServicio != null) {
        servicio = new Servicio();
        servicio.setId((Integer) datosServicio[0]);
        servicio.setNombre(datosServicio[1].toString());
        servicio.setDescripcion(datosServicio[2].toString());
        servicio.setPrecio((Double) datosServicio[3]);
        servicio.setDuracionMinutos((Integer) datosServicio[4]);
        servicio.setTipoServicio(TipoServicio.valueOf(datosServicio[5].toString()));
        servicio.setActivo(datosServicio[6].toString().equals("Activo"));
        titulo = "Editar Servicio";
    }
    
    Window parentWindow = SwingUtilities.getWindowAncestor(this);
    JFrame parentFrame = null;
    if (parentWindow instanceof JFrame) {
        parentFrame = (JFrame) parentWindow;
    }
    
    ServicioDialog dialog = new ServicioDialog(parentFrame, titulo, servicio);
    dialog.setVisible(true);
    
    if (dialog.isGuardadoExitoso()) {
        // Actualizar la tabla inmediatamente después de guardar
        actualizarTabla();
        JOptionPane.showMessageDialog(this, 
            "Los cambios se han guardado correctamente.", 
            "Guardado Exitoso", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}

    private void editarServicioSeleccionado() {
    int filaSeleccionada = tablaServicios.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this,
            "Por favor, seleccione un servicio para editar.",
            "Selección Requerida",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Obtener datos del servicio seleccionado
    Object[] datosServicio = new Object[tableModel.getColumnCount()];
    for (int i = 0; i < tableModel.getColumnCount(); i++) {
        datosServicio[i] = tableModel.getValueAt(filaSeleccionada, i);
    }

    abrirDialogoServicio(datosServicio); // Pasar los datos para editar
}

    private void eliminarServicioSeleccionado() {
    int filaSeleccionada = tablaServicios.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this,
            "Por favor, seleccione un servicio para eliminar.",
            "Selección Requerida",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Obtener el ID del servicio seleccionado
    int idServicio = (Integer) tableModel.getValueAt(filaSeleccionada, 0);
    String nombreServicio = tableModel.getValueAt(filaSeleccionada, 1).toString();

    int confirmacion = JOptionPane.showConfirmDialog(this,
        "¿Está seguro que desea eliminar el servicio:\n" + nombreServicio + "?\n\n" +
        "Esta acción no se puede deshacer.",
        "Confirmar Eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);

    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            // ELIMINAR DE LA BASE DE DATOS
            ServicioController controller = new ServicioController();
            controller.eliminarServicio(idServicio);
            
            // Eliminar de la tabla visual
            tableModel.removeRow(filaSeleccionada);
            
            JOptionPane.showMessageDialog(this,
                "Servicio eliminado correctamente.",
                "Eliminación Exitosa",
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar servicio: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void cambiarEstadoServicio() {
        int fila = tablaServicios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String estado = tableModel.getValueAt(fila, 6).toString();
        tableModel.setValueAt(estado.equals("Activo") ? "Inactivo" : "Activo", fila, 6);
    }

    private void filtrarPorTipo() {
    String tipoSeleccionado = (String) comboFiltroTipo.getSelectedItem();
    if (tipoSeleccionado.equals("Todos")) {
        actualizarTabla();
        return;
    }

    try {
        tableModel.setRowCount(0); // Limpiar tabla
        
        ServicioController controller = new ServicioController();
        List<Servicio> servicios = controller.buscarServiciosPorTipo(tipoSeleccionado);
        
        for (Servicio servicio : servicios) {
            Object[] fila = {
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getDuracionMinutos(),
                servicio.getTipoServicio().name(),
                servicio.isActivo() ? "Activo" : "Inactivo"
            };
            tableModel.addRow(fila);
        }
        
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron servicios del tipo: " + tipoSeleccionado,
                "Sin Resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }
        
    } catch (ServiceException e) {
        JOptionPane.showMessageDialog(this,
            "Error al filtrar servicios: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

private void buscarServicios() {
    String textoBusqueda = txtBuscar.getText().trim().toLowerCase();
    if (textoBusqueda.isEmpty()) {
        actualizarTabla();
        return;
    }

    try {
        tableModel.setRowCount(0); // Limpiar tabla
        
        ServicioController controller = new ServicioController();
        List<Servicio> todosServicios = controller.obtenerTodosServicios();
        
        // Filtrar localmente por nombre o descripción
        for (Servicio servicio : todosServicios) {
            if (servicio.getNombre().toLowerCase().contains(textoBusqueda) ||
                servicio.getDescripcion().toLowerCase().contains(textoBusqueda)) {
                
                Object[] fila = {
                    servicio.getId(),
                    servicio.getNombre(),
                    servicio.getDescripcion(),
                    servicio.getPrecio(),
                    servicio.getDuracionMinutos(),
                    servicio.getTipoServicio().name(),
                    servicio.isActivo() ? "Activo" : "Inactivo"
                };
                tableModel.addRow(fila);
            }
        }
        
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron servicios que coincidan con: " + textoBusqueda,
                "Sin Resultados",
                JOptionPane.INFORMATION_MESSAGE);
            actualizarTabla();
        }
        
    } catch (ServiceException e) {
        JOptionPane.showMessageDialog(this,
            "Error al buscar servicios: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * Actualiza la tabla de servicios con los datos más recientes de la base de datos.
     * Limpia la tabla actual y la llena con todos los servicios registrados.
     */
    public void actualizarTabla() {
    try {
        tableModel.setRowCount(0); // Limpiar tabla
        
        // Obtener servicios REALES de la base de datos
        ServicioController controller = new ServicioController();
        List<Servicio> servicios = controller.obtenerTodosServicios();
        
        System.out.println("🔄 Actualizando tabla - Servicios encontrados: " + servicios.size()); // DEBUG
        
        // Llenar la tabla con datos REALES
        for (Servicio servicio : servicios) {
            Object[] fila = {
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getDuracionMinutos(),
                servicio.getTipoServicio().name(),
                servicio.isActivo() ? "Activo" : "Inactivo"
            };
            tableModel.addRow(fila);
        }
        
        System.out.println("✅ Tabla actualizada con " + tableModel.getRowCount() + " servicios"); // DEBUG
        
    } catch (ServiceException e) {
        System.err.println("❌ Error al actualizar tabla: " + e.getMessage());
        JOptionPane.showMessageDialog(this, 
            "Error al cargar servicios: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
}