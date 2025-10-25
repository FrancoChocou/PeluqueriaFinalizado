package com.idra.gestionpeluqueria.view.panels;

import com.idra.gestionpeluqueria.controller.ClienteController;
import com.idra.gestionpeluqueria.controller.ServicioController;
import com.idra.gestionpeluqueria.controller.TurnoController;
import com.idra.gestionpeluqueria.exception.ServiceException;
import com.idra.gestionpeluqueria.model.Cliente;
import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.Turno;
import com.idra.gestionpeluqueria.model.enums.EstadoPago;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel principal (dashboar) de la aplicacion
 * Muestra estadisticas generales del negocio incluyendo total de clientes,
 * servicios activos, turnos del dia e ingresos. Tambien proporciona acceso 
 * rapido a las funciones principales y muestra los turnos del dia actual.
 * 
 * @author Idra
 */

public class DashboardPanel extends JPanel {
    private JLabel lblTotalClientes, lblTotalServicios, lblTurnosHoy, lblIngresosHoy;
    private JLabel lblFechaActual;
    private JPanel statsPanel, quickActionsPanel, recentTurnosPanel;
    
    /**
     * Constructor que inicializa el panel del dashboard y sus componentes.
     */
    public DashboardPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel superior con fecha y título
        createHeaderPanel();

        // Panel de estadísticas
        createStatsPanel();

        // Panel de acciones rápidas
        createQuickActionsPanel();

        // Panel de turnos recientes
        createRecentTurnosPanel();

        // Layout principal
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new BorderLayout(0, 20));
        topPanel.add(statsPanel, BorderLayout.CENTER);
        topPanel.add(quickActionsPanel, BorderLayout.EAST);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(recentTurnosPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Dashboard Principal");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(50, 50, 50));

        lblFechaActual = new JLabel();
        lblFechaActual.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFechaActual.setForeground(new Color(100, 100, 100));
        actualizarFecha();

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(lblFechaActual, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    private void createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBackground(new Color(240, 240, 240));

        // Tarjeta 1: Total Clientes
        JPanel cardClientes = createStatCard("👥 Total Clientes", "0", new Color(41, 128, 185));
        lblTotalClientes = (JLabel) ((JPanel) cardClientes.getComponent(1)).getComponent(0);

        // Tarjeta 2: Total Servicios
        JPanel cardServicios = createStatCard("✂️ Servicios Activos", "0", new Color(39, 174, 96));
        lblTotalServicios = (JLabel) ((JPanel) cardServicios.getComponent(1)).getComponent(0);

        // Tarjeta 3: Turnos Hoy
        JPanel cardTurnos = createStatCard("📅 Turnos Hoy", "0", new Color(243, 156, 18));
        lblTurnosHoy = (JLabel) ((JPanel) cardTurnos.getComponent(1)).getComponent(0);

        // Tarjeta 4: Ingresos Hoy
        JPanel cardIngresos = createStatCard("💰 Ingresos Hoy", "$0.00", new Color(231, 76, 60));
        lblIngresosHoy = (JLabel) ((JPanel) cardIngresos.getComponent(1)).getComponent(0);

        statsPanel.add(cardClientes);
        statsPanel.add(cardServicios);
        statsPanel.add(cardTurnos);
        statsPanel.add(cardIngresos);
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 100));

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setBackground(Color.WHITE);
        valuePanel.add(valueLabel, BorderLayout.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);

        return card;
    }

   private void createQuickActionsPanel() {
    quickActionsPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Cambié de 4,1 a 3,1
    quickActionsPanel.setBackground(new Color(240, 240, 240));
    quickActionsPanel.setPreferredSize(new Dimension(250, 0));

    JButton btnNuevoCliente = createActionButton("➕ Nuevo Cliente", new Color(41, 128, 185));
    JButton btnNuevoServicio = createActionButton("🎨 Nuevo Servicio", new Color(39, 174, 96));
    JButton btnNuevoTurno = createActionButton("📋 Nuevo Turno", new Color(243, 156, 18));

    quickActionsPanel.add(btnNuevoCliente);
    quickActionsPanel.add(btnNuevoServicio);
    quickActionsPanel.add(btnNuevoTurno);

    // Event listeners
    btnNuevoCliente.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Abrir diálogo para nuevo cliente", 
            "Nuevo Cliente", 
            JOptionPane.INFORMATION_MESSAGE);
    });
    
    btnNuevoServicio.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Abrir diálogo para nuevo servicio", 
            "Nuevo Servicio", 
            JOptionPane.INFORMATION_MESSAGE);
    });
    
    btnNuevoTurno.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Abrir diálogo para nuevo turno", 
            "Nuevo Turno", 
            JOptionPane.INFORMATION_MESSAGE);
    });
}

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
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

    private void createRecentTurnosPanel() {
    recentTurnosPanel = new JPanel(new BorderLayout());
    recentTurnosPanel.setBackground(Color.WHITE);
    recentTurnosPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder("📋 Turnos de Hoy"),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));

    try {
        // Obtener turnos de HOY desde la base de datos
        TurnoController controller = new TurnoController();
        List<Turno> turnosHoy = controller.buscarTurnosPorFecha(LocalDate.now());
        
        String[] columnNames = {"Hora", "Cliente", "Servicio", "Estado", "Pago"};
        Object[][] data;
        
        if (turnosHoy.isEmpty()) {
            data = new Object[][]{
                {"--:--", "No hay turnos para hoy", "---", "---", "---"}
            };
        } else {
            data = new Object[turnosHoy.size()][5];
            for (int i = 0; i < turnosHoy.size(); i++) {
                Turno turno = turnosHoy.get(i);
                data[i][0] = turno.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm"));
                data[i][1] = turno.getCliente().getNombre() + " " + turno.getCliente().getApellido();
                data[i][2] = turno.getServicio().getNombre();
                data[i][3] = turno.getEstado().getDescripcion();
                data[i][4] = turno.getEstadoPago().getDescripcion();
            }
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setEnabled(false); // Solo lectura

        JScrollPane scrollPane = new JScrollPane(table);
        recentTurnosPanel.add(scrollPane, BorderLayout.CENTER);
        
    } catch (ServiceException e) {
        System.err.println("❌ Error al cargar turnos de hoy: " + e.getMessage());
        // Mostrar tabla vacía en caso de error
        String[] columnNames = {"Hora", "Cliente", "Servicio", "Estado", "Pago"};
        Object[][] data = {{"--:--", "Error al cargar turnos", "---", "---", "---"}};
        JTable table = new JTable(data, columnNames);
        recentTurnosPanel.add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
   /**
     * Actualiza todos los datos mostrados en el dashboard.
     * Refresca las estadísticas, la fecha actual y la tabla de turnos del día
     * con información actualizada desde la base de datos.
     */
   public void actualizarDatos() {
    actualizarFecha();
    
    try {
        // Actualizar estadísticas con datos reales
        ClienteController clienteController = new ClienteController();
        ServicioController servicioController = new ServicioController();
        TurnoController turnoController = new TurnoController();
        
        List<Cliente> clientes = clienteController.obtenerTodosClientes();
        List<Servicio> servicios = servicioController.obtenerServiciosActivos();
        List<Turno> turnosHoy = turnoController.buscarTurnosPorFecha(LocalDate.now());
        
        // Calcular ingresos de hoy
        double ingresosHoy = turnosHoy.stream()
            .filter(turno -> turno.getEstadoPago() == EstadoPago.PAGADO)
            .mapToDouble(turno -> turno.getMontoPagado())
            .sum();
        
        // Actualizar labels
        lblTotalClientes.setText(String.valueOf(clientes.size()));
        lblTotalServicios.setText(String.valueOf(servicios.size()));
        lblTurnosHoy.setText(String.valueOf(turnosHoy.size()));
        lblIngresosHoy.setText("$" + String.format("%.2f", ingresosHoy));
        
        // Actualizar tabla de turnos de hoy
        updateTurnosHoyTable();
        
    } catch (ServiceException e) {
        System.err.println("❌ Error al actualizar datos del dashboard: " + e.getMessage());
        // Valores por defecto en caso de error
        lblTotalClientes.setText("0");
        lblTotalServicios.setText("0");
        lblTurnosHoy.setText("0");
        lblIngresosHoy.setText("$0.00");
    }
}
   
   
   private void updateTurnosHoyTable() {
    try {
        TurnoController controller = new TurnoController();
        List<Turno> turnosHoy = controller.buscarTurnosPorFecha(LocalDate.now());
        
        // Si el panel ya existe, actualizarlo
        if (recentTurnosPanel.getComponentCount() > 0) {
            recentTurnosPanel.removeAll();
            
            String[] columnNames = {"Hora", "Cliente", "Servicio", "Estado", "Pago"};
            Object[][] data;
            
            if (turnosHoy.isEmpty()) {
                data = new Object[][]{
                    {"--:--", "No hay turnos para hoy", "---", "---", "---"}
                };
            } else {
                data = new Object[turnosHoy.size()][5];
                for (int i = 0; i < turnosHoy.size(); i++) {
                    Turno turno = turnosHoy.get(i);
                    data[i][0] = turno.getFechaHora().format(DateTimeFormatter.ofPattern("HH:mm"));
                    data[i][1] = turno.getCliente().getNombre() + " " + turno.getCliente().getApellido();
                    data[i][2] = turno.getServicio().getNombre();
                    data[i][3] = turno.getEstado().getDescripcion();
                    data[i][4] = turno.getEstadoPago().getDescripcion();
                }
            }
            
            JTable table = new JTable(data, columnNames);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            table.setRowHeight(30);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table.setEnabled(false);
            
            recentTurnosPanel.add(new JScrollPane(table), BorderLayout.CENTER);
            recentTurnosPanel.revalidate();
            recentTurnosPanel.repaint();
        }
        
    } catch (ServiceException e) {
        System.err.println("❌ Error al actualizar tabla de turnos de hoy: " + e.getMessage());
    }
}

    private void actualizarFecha() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy");
        String fechaFormateada = LocalDate.now().format(formatter);
        lblFechaActual.setText(fechaFormateada);
    }
}