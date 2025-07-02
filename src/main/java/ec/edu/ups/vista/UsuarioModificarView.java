package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UsuarioModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JLabel lblContraseña;
    private JTextField txtContraseña;
    private JLabel lblRol;
    private JComboBox<Rol> cbxRoles;
    private JButton btnModificar;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioModificarView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setSize(600, 400);
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlEliminar=getClass().getResource("/trash.png");

        btnBuscar.setIcon(new ImageIcon(urlBuscar));
        btnModificar.setIcon(new ImageIcon(urlEliminar));

        actualizarTextos();
        cargarRoles();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.modificar.titulo.app"));

        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");
        lblTitulo.setText(mensajes.get("usuario.modificar.titulo.app"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));
        txtContraseña.setToolTipText(mensajes.get("usuario.crear.contraseña"));

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
            cbxRoles.addItem(rol);
        }
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JComboBox<Rol> getCbxRoles() {
        return cbxRoles;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        if (cbxRoles.getItemCount() > 0) {
            cbxRoles.setSelectedIndex(0);
        }
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
        txtContraseña.setEnabled(false);
        cbxRoles.setEnabled(false);
        btnModificar.setEnabled(false);
    }
}