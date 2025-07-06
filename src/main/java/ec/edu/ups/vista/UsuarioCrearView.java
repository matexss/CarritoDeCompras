package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioCrearView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblUsuarioA;
    private JLabel lblContraseña;
    private JLabel lblRol;

    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JComboBox<Rol> cbxRoles;
    private JButton btnCrear;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioCrearView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        initComponents();
        actualizarTextos();
        cargarRoles();
    }

    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Título
        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel central del formulario
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblUsuarioA = new JLabel();
        lblContraseña = new JLabel();
        lblRol = new JLabel();

        txtUsuario = new JTextField();
        txtContraseña = new JTextField();
        cbxRoles = new JComboBox<>();
        btnCrear = new JButton();

        formPanel.add(lblUsuarioA);
        formPanel.add(txtUsuario);
        formPanel.add(lblContraseña);
        formPanel.add(txtContraseña);
        formPanel.add(lblRol);
        formPanel.add(cbxRoles);
        formPanel.add(new JLabel()); // Espacio vacío
        formPanel.add(btnCrear);

        add(formPanel, BorderLayout.CENTER);
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.crear.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.crear.titulo.app"));

        lblUsuarioA.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");

        txtUsuario.setToolTipText(mensajes.get("usuario.crear.nombre"));
        txtContraseña.setToolTipText(mensajes.get("usuario.crear.contraseña"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));

        btnCrear.setText(mensajes.get("global.crear"));

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Rol) {
                    Rol rol = (Rol) value;
                    if (rol == Rol.ADMINISTRADOR) {
                        setText(mensajes.get("global.rol.admin"));
                    } else if (rol == Rol.USUARIO) {
                        setText(mensajes.get("global.rol.user"));
                    }
                }
                return this;
            }
        });
    }

    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            if (rol == Rol.ADMINISTRADOR || rol == Rol.USUARIO) {
                cbxRoles.addItem(rol);
            }
        }
    }


    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public JComboBox<Rol> getCbxRoles() {
        return cbxRoles;
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        cbxRoles.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
