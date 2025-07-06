package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;

public class UsuarioActualizarView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnGuardar;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton ACTUALIZARButton;

    public UsuarioActualizarView() {
        setTitle("Actualizar Datos del Usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Fecha de nacimiento (dd/MM/yyyy):"));
        txtFechaNacimiento = new JTextField();
        add(txtFechaNacimiento);

        add(new JLabel("Correo electrónico:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel("Nueva contraseña (opcional):"));
        txtNuevaContrasenia = new JPasswordField();
        add(txtNuevaContrasenia);

        btnGuardar = new JButton("Guardar cambios");
        add(new JLabel()); // espacio vacío
        add(btnGuardar);
    }

    // Getters
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtFechaNacimiento() { return txtFechaNacimiento; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JPasswordField getTxtNuevaContrasenia() { return txtNuevaContrasenia; }
    public JButton getBtnGuardar() { return btnGuardar; }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
