package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioModificarMisView extends JInternalFrame {
    private JPanel panel1;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JTextField txtUsuario;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JTextField txtNuevoUser;
    private JLabel lblNuevoUser;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;

        inicializarComponentes();
        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        txtUsuario.setEditable(false);
        actualizarTextos();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(5, 2, 10, 10));
        lblTitulo = new JLabel();
        lblNuevoUser = new JLabel();
        lblUsuario = new JLabel();
        lblContraseña = new JLabel();
        txtUsuario = new JTextField();
        txtNuevoUser = new JTextField();
        txtContraseña = new JTextField();
        btnModificar = new JButton();

        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(new JLabel()); // Espaciador

        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);

        panelPrincipal.add(lblNuevoUser);
        panelPrincipal.add(txtNuevoUser);

        panelPrincipal.add(lblContraseña);
        panelPrincipal.add(txtContraseña);

        panelPrincipal.add(new JLabel()); // Espaciador
        panelPrincipal.add(btnModificar);
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("menu.usuario.modificarMis"));
        lblTitulo.setText(mensajes.get("menu.usuario.modificarMis"));
        lblNuevoUser.setText(mensajes.get("global.nuevoUsuario") + ":");
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    public JTextField getTxtNuevoUser() {
        return txtNuevoUser;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtContraseña.setText("");
        txtNuevoUser.setText("");
    }
}
