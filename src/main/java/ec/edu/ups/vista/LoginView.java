package ec.edu.ups.vista;

import ec.edu.ups.util.IconUtil;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnLogin;
    private JButton btnRegistrarse;
    private JButton btnRecuperarContrasenia;

    public LoginView() {
        setTitle("Login");
        Color fondo = new Color(215, 159, 175);
        getContentPane().setBackground(fondo);
        setLayout(null);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(30, 30, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 180, 25);
        add(txtUsuario);

        JLabel lblContrasenia = new JLabel("Contraseña:");
        lblContrasenia.setBounds(30, 70, 80, 25);
        add(lblContrasenia);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(120, 70, 180, 25);
        add(txtContrasenia);

        btnLogin = new JButton("Iniciar sesión", IconUtil.cargarIcono("logout.png", 18, 18));
        btnLogin.setBounds(30, 120, 130, 30);
        add(btnLogin);

        btnRegistrarse = new JButton("Registrarse", IconUtil.cargarIcono("user-add.png", 18, 18));
        btnRegistrarse.setBounds(170, 120, 130, 30);
        add(btnRegistrarse);

        btnRecuperarContrasenia = new JButton("¿Olvidó su contraseña?", IconUtil.cargarIcono("search.png", 18, 18));
        btnRecuperarContrasenia.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRecuperarContrasenia.setBounds(80, 170, 180, 25);
        add(btnRecuperarContrasenia);
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public JButton getBtnRecuperarContrasenia() {
        return btnRecuperarContrasenia;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
