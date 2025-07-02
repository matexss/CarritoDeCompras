package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.net.URL;

public class UsuarioModificarMisView extends JInternalFrame {
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JTextField txtUsuario;
    private JPanel panelPrincipal;
    private JPanel panel1;
    private JLabel lblTitulo;
    private JTextField txtNuevoUser;
    private JLabel lblNuevoUser;

    private MensajeInternacionalizacionHandler mensajes;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensajes) {

        super("", true, true, false, true);
        this.mensajes = mensajes;

        URL urlModificar = getClass().getResource("/edit.png");
        btnModificar.setIcon(new ImageIcon(urlModificar));

        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);

        txtUsuario.setEditable(false);

        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("menu.usuario.modificarMis"));
        lblTitulo.setText(mensajes.get("menu.usuario.modificarMis"));
        lblNuevoUser.setText(mensajes.get("global.nuevoUsuario"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    public JTextField getTxtNuevoUser() {
        return txtNuevoUser;
    }

    public void setTxtNuevoUser(JTextField txtNuevoUser) {
        this.txtNuevoUser = txtNuevoUser;
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
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}