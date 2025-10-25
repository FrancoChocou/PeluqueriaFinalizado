package com.idra.gestionpeluqueria.view.dialogs;

import com.idra.gestionpeluqueria.model.Servicio;
import com.idra.gestionpeluqueria.model.enums.TipoServicio;
import com.idra.gestionpeluqueria.controller.ServicioController;
import com.idra.gestionpeluqueria.exception.ServiceException;

import javax.swing.*;
import java.awt.*;
/**
 * Dialogo para crear y editar servicios de peluqueria.
 * Proporciona un formulario con validaciones para ingresar 
 * la informacion de un servicio nuevo o modificar uno existente.
 * 
 * @author Idra
 */
public class ServicioDialog extends JDialog {
    private JTextField txtNombre, txtDescripcion, txtPrecio, txtDuracion;
    private JComboBox<TipoServicio> comboTipo;
    private JCheckBox chkActivo;
    private JButton btnGuardar, btnCancelar;
    private final ServicioController servicioController;
    private final Servicio servicioEditar;
    private boolean guardadoExitoso;
    
    /**
     * Constructor que crea el diálogo para agregar o editar un servicio.
     * 
     * @param parent El frame padre del diálogo
     * @param titulo El título a mostrar en el diálogo
     * @param servicioEditar El servicio a editar, o null para crear uno nuevo
     */
    public ServicioDialog(JFrame parent, String titulo, Servicio servicioEditar) {
        super(parent, titulo, true);
        this.servicioEditar = servicioEditar;
        this.servicioController = new ServicioController();
        this.guardadoExitoso = false;
        
        initializeUI();
        if (servicioEditar != null) {
            cargarDatosServicio();
        }
    }

    private void initializeUI() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Servicio"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, gbc);
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        txtDescripcion = new JTextField(20);
        formPanel.add(txtDescripcion, gbc);
        
        // Precio
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField(20);
        formPanel.add(txtPrecio, gbc);
        
        // Duración
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Duración (minutos):"), gbc);
        gbc.gridx = 1;
        txtDuracion = new JTextField(20);
        formPanel.add(txtDuracion, gbc);
        
        // Tipo Servicio
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Tipo Servicio:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(TipoServicio.values());
        formPanel.add(comboTipo, gbc);
        
        // Activo
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1;
        chkActivo = new JCheckBox();
        chkActivo.setSelected(true);
        formPanel.add(chkActivo, gbc);
        
        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnGuardar = new JButton("💾 Guardar");
        btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        
        btnCancelar.setBackground(new Color(231, 76, 60));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        btnGuardar.addActionListener(e -> guardarServicio());
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnGuardar);
        
        return buttonPanel;
    }

    private void cargarDatosServicio() {
        if (servicioEditar != null) {
            txtNombre.setText(servicioEditar.getNombre());
            txtDescripcion.setText(servicioEditar.getDescripcion());
            txtPrecio.setText(String.valueOf(servicioEditar.getPrecio()));
            txtDuracion.setText(String.valueOf(servicioEditar.getDuracionMinutos()));
            comboTipo.setSelectedItem(servicioEditar.getTipoServicio());
            chkActivo.setSelected(servicioEditar.isActivo());
        }
    }

    private void guardarServicio() {
    try {
        System.out.println("🔄 Iniciando guardado..."); // DEBUG
        System.out.println("📝 Modo: " + (servicioEditar == null ? "AGREGAR" : "EDITAR")); // DEBUG
        
        if (!validarCampos()) {
            return;
        }
        
        if (servicioEditar == null) {
            System.out.println("➕ Creando NUEVO servicio..."); // DEBUG
            Servicio nuevoServicio = new Servicio(
                txtNombre.getText().trim(),
                txtDescripcion.getText().trim(),
                Double.parseDouble(txtPrecio.getText().trim()),
                Integer.parseInt(txtDuracion.getText().trim()),
                (TipoServicio) comboTipo.getSelectedItem()
            );
            nuevoServicio.setActivo(chkActivo.isSelected());
            
            servicioController.crearServicio(nuevoServicio);
            System.out.println("✅ Servicio creado en BD"); // DEBUG
            
            JOptionPane.showMessageDialog(this, "Servicio creado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("✏️ Editando servicio ID: " + servicioEditar.getId()); // DEBUG
            
            // Guardar los valores antiguos para debug
            System.out.println("📊 Valores ANTES:");
            System.out.println("  - Nombre: " + servicioEditar.getNombre());
            System.out.println("  - Precio: " + servicioEditar.getPrecio());
            
            // Actualizar el objeto
            servicioEditar.setNombre(txtNombre.getText().trim());
            servicioEditar.setDescripcion(txtDescripcion.getText().trim());
            servicioEditar.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            servicioEditar.setDuracionMinutos(Integer.parseInt(txtDuracion.getText().trim()));
            servicioEditar.setTipoServicio((TipoServicio) comboTipo.getSelectedItem());
            servicioEditar.setActivo(chkActivo.isSelected());
            
            System.out.println("📊 Valores DESPUÉS:");
            System.out.println("  - Nombre: " + servicioEditar.getNombre());
            System.out.println("  - Precio: " + servicioEditar.getPrecio());
            
            // Llamar al controller para actualizar en BD
            servicioController.actualizarServicio(servicioEditar);
            System.out.println("✅ Servicio actualizado en BD"); // DEBUG
            
            JOptionPane.showMessageDialog(this, "Servicio actualizado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
        
        guardadoExitoso = true;
        dispose();
        
    } catch (ServiceException | NumberFormatException ex) {
        System.err.println("❌ Error al guardar: " + ex.getMessage()); // DEBUG
        ex.printStackTrace(); // DEBUG
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio <= 0) {
                mostrarError("El precio debe ser mayor a 0");
                txtPrecio.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El precio debe ser un número válido");
            txtPrecio.requestFocus();
            return false;
        }
        
        try {
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            if (duracion <= 0) {
                mostrarError("La duración debe ser mayor a 0");
                txtDuracion.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("La duración debe ser un número válido");
            txtDuracion.requestFocus();
            return false;
        }
        
        return true;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de Validación", JOptionPane.WARNING_MESSAGE);
    }
    /**
     * Verifica si el servicio fue guardado exitosamente.
     * 
     * @return true si el guardado fue exitoso, false en caso contrario
     */
    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }
}