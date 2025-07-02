package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UsuarioCrearView extends JInternalFrame {
    private JLabel lblUsuarioA;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JButton btnCrear;
    private JComboBox<Rol> cbxRoles;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensajes;

    public UsuarioCrearView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        URL urlCrear = getClass().getResource("/plus.png");
        btnCrear.setIcon(new ImageIcon(urlCrear));
        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);

        actualizarTextos();
        cargarRoles();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("usuario.crear.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.crear.titulo.app"));
        lblUsuarioA.setText(mensajes.get("global.usuario")+": ");
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
            cbxRoles.addItem(rol);
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
        if (cbxRoles.getItemCount() > 0) {
            cbxRoles.setSelectedIndex(0);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}