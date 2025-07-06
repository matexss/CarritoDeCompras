package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioEliminarView extends JInternalFrame {

    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JButton btnEliminar;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super(mensajes.get("usuario.eliminar.titulo.app"), true, true, true, true);
        this.mensajes = mensajes;

        initComponents();
        actualizarTextos();
    }

    private void initComponents() {
        setSize(400, 200);
        setLayout(new BorderLayout());

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblUsuario = new JLabel();
        txtUsuario = new JTextField();
        btnEliminar = new JButton();

        panelCentro.add(lblUsuario);
        panelCentro.add(txtUsuario);
        panelCentro.add(new JLabel()); // Espacio vacío
        panelCentro.add(btnEliminar);

        add(panelCentro, BorderLayout.CENTER);
    }

    private void actualizarTextos() {
        setTitle(mensajes.get("usuario.eliminar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.eliminar.titulo.app"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
    }
}
