package ec.edu.ups.vista;

import javax.swing.*;

public class RegistroView extends JFrame {

    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JComboBox comboBox1;
    private JButton btnRegistrar;

    public RegistroView() {
        setTitle("Registro de Usuario");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
    }

    public JTextField getTxtUsername() {
        return textField1;
    }

    public JPasswordField getTxtContrasenia() {
        return passwordField1;
    }

    public JComboBox getComboRol() {
        return comboBox1;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
