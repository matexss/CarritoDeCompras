package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class UsuarioEliminarView extends JInternalFrame {
    private JLabel lblUsuario;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JTextField txtContraseña;
    private JButton btnEliminar;
    private JTextField txtRol;
    private JPanel paneltitulo;
    private JPanel panelPrincipal;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        this.mensajes = mensajes;

        setSize(600, 400);
        setContentPane(panelPrincipal);
        URL urlBuscar = getClass().getResource("/search.png");
        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        URL urlEliminar = getClass().getResource("/trash.png");
        btnEliminar.setIcon(new ImageIcon(urlEliminar));


        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.eliminar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.eliminar.titulo.app"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");
        txtUsuario.setToolTipText(mensajes.get("usuario.crear.nombre"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTextField getTxtRol() {
        return txtRol;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        txtRol.setText("");
        btnEliminar.setEnabled(false);
        txtContraseña.setEnabled(false);
        txtRol.setEnabled(false);
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
    }
}