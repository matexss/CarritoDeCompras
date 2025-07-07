package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioModificarView extends JInternalFrame implements ActualizableConIdioma {

    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JComboBox<Rol> cbxRoles;
    private JButton btnBuscar;
    private JButton btnModificar;

    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setSize(500, 300);
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        lblUsuario = new JLabel();
        lblContraseña = new JLabel();
        lblRol = new JLabel();

        txtUsuario = new JTextField();
        txtContraseña = new JTextField();
        cbxRoles = new JComboBox<>(Rol.values());

        btnBuscar = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        btnBuscar.setPreferredSize(new Dimension(160, 35));

        btnModificar = new JButton(IconUtil.cargarIcono("editar.png", 18, 18));
        btnModificar.setPreferredSize(new Dimension(160, 35));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblContraseña, gbc);
        gbc.gridx = 1;
        formPanel.add(txtContraseña, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblRol, gbc);
        gbc.gridx = 1;
        formPanel.add(cbxRoles, gbc);

        panelPrincipal.add(formPanel, BorderLayout.CENTER);

        botonPanel.add(btnBuscar);
        botonPanel.add(btnModificar);
        panelPrincipal.add(botonPanel, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Rol) {
                    Rol rol = (Rol) value;
                    setText(rol == Rol.ADMINISTRADOR
                            ? mensajes.get("global.rol.admin")
                            : mensajes.get("global.rol.user"));
                }
                return this;
            }
        });
    }

    @Override
    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.modificar.titulo.app"));

        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");

        txtUsuario.setToolTipText(mensajes.get("usuario.modificar.usuario"));
        txtContraseña.setToolTipText(mensajes.get("usuario.modificar.nuevaContraseña"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    public JTextField getTxtUsuario() { return txtUsuario; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JComboBox<Rol> getCbxRoles() { return cbxRoles; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnModificar() { return btnModificar; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        cbxRoles.setSelectedIndex(0);
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
        txtContraseña.setEnabled(false);
        cbxRoles.setEnabled(false);
        btnModificar.setEnabled(false);
    }
}
