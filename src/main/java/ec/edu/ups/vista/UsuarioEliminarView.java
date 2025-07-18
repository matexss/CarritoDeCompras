package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioEliminarView extends JInternalFrame implements ActualizableConIdioma {

    private JLabel lblTitulo, lblUsuario;
    private JTextField txtUsuario;
    private JButton btnEliminar, button1;
    private JTextField textField1;
    private JTextField textField2;
    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        initComponents();
        actualizarTextos(mensajes);
    }

    private void initComponents() {
        setSize(400, 220);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 232));

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(80, 20, 60));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelCentro.setOpaque(false);

        lblUsuario = new JLabel();
        txtUsuario = new JTextField();

        btnEliminar = new JButton("Eliminar", IconUtil.cargarIcono("user-delete.png", 18, 18));

        panelCentro.add(lblUsuario);
        panelCentro.add(txtUsuario);
        panelCentro.add(new JLabel());
        panelCentro.add(btnEliminar);

        add(panelCentro, BorderLayout.CENTER);
    }

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
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
