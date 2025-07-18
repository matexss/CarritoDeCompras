package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioModificarMisView extends JInternalFrame implements ActualizableConIdioma {
    private JPanel panelPrincipal;
    private JPanel panel1;
    private JLabel lblUsuario, lblContraseña, lblTitulo, lblNuevoUser;
    private JTextField txtUsuario, txtContraseña, txtNuevoUser;
    private JButton btnModificar;
    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;

        inicializarComponentes();
        setContentPane(panelPrincipal);
        setSize(500, 300);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        txtUsuario.setEditable(false);
        actualizarTextos(mensajes);
    }

    private void inicializarComponentes() {
        Color fondo = new Color(255, 228, 232);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(fondo);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(fondo);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonPanel.setBackground(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTitulo = new JLabel();
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(80, 20, 60));

        lblUsuario = new JLabel();
        txtUsuario = new JTextField();

        lblNuevoUser = new JLabel();
        txtNuevoUser = new JTextField();

        lblContraseña = new JLabel();
        txtContraseña = new JTextField();

        btnModificar = new JButton(IconUtil.cargarIcono("user-update.png", 18, 18));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblNuevoUser, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNuevoUser, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblContraseña, gbc);
        gbc.gridx = 1;
        formPanel.add(txtContraseña, gbc);

        botonPanel.add(btnModificar);

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(formPanel, BorderLayout.CENTER);
        panelPrincipal.add(botonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("menu.usuario.modificarMis"));
        lblTitulo.setText(mensajes.get("menu.usuario.modificarMis"));
        lblNuevoUser.setText(mensajes.get("global.nuevoUsuario") + ":");
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");

        txtNuevoUser.setToolTipText(mensajes.get("usuario.modificar.usuario"));
        txtContraseña.setToolTipText(mensajes.get("usuario.modificar.nuevaContraseña"));

        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    public JTextField getTxtNuevoUser() { return txtNuevoUser; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JButton getBtnModificar() { return btnModificar; }
    public JTextField getTxtUsuario() { return txtUsuario; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtContraseña.setText("");
        txtNuevoUser.setText("");
    }
}
