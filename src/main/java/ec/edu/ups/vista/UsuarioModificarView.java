package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioModificarView extends JInternalFrame {

    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JComboBox<Rol> cbxRoles;
    private JButton btnBuscar;
    private JButton btnModificar;

    public UsuarioModificarView(MensajeInternacionalizacionHandler mensajes) {
        super(mensajes.get("usuario.modificar.titulo.app"), true, true, true, true);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        txtUsuario = new JTextField();
        txtContraseña = new JTextField();
        cbxRoles = new JComboBox<>(Rol.values());

        btnBuscar = new JButton(mensajes.get("global.boton.buscar"));
        btnModificar = new JButton(mensajes.get("global.boton.modificar"));

        add(new JLabel(mensajes.get("global.usuario") + ":"));
        add(txtUsuario);

        add(new JLabel(mensajes.get("global.contraseña") + ":"));
        add(txtContraseña);

        add(new JLabel(mensajes.get("global.rol") + ":"));
        add(cbxRoles);

        add(btnBuscar);
        add(btnModificar);

        setVisible(true);
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JComboBox<Rol> getCbxRoles() {
        return cbxRoles;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
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
