package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class LoginView extends JFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnLogin;
    private JButton btnRegistrarse;
    private JButton btnRecuperarContrasenia;
    private JComboBox<String> comboIdioma;

    private JLabel lblUsuario;
    private JLabel lblContrasenia;
    private JLabel lblIdioma;

    private MensajeInternacionalizacionHandler mensajes;

    public LoginView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;

        setTitle("Login");
        Color fondo = new Color(215, 159, 175);
        getContentPane().setBackground(fondo);
        setLayout(null);

        panelPrincipal = new JPanel(null);
        panelPrincipal.setBounds(0, 0, 350, 250);
        panelPrincipal.setOpaque(false);
        add(panelPrincipal);

        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblUsuario = new JLabel();
        lblUsuario.setBounds(30, 30, 80, 25);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 180, 25);

        lblContrasenia = new JLabel();
        lblContrasenia.setBounds(30, 70, 80, 25);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(120, 70, 180, 25);

        btnLogin = new JButton(IconUtil.cargarIcono("logout.png", 18, 18));
        btnLogin.setBounds(30, 120, 130, 30);

        btnRegistrarse = new JButton(IconUtil.cargarIcono("user-add.png", 18, 18));
        btnRegistrarse.setBounds(170, 120, 130, 30);

        btnRecuperarContrasenia = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        btnRecuperarContrasenia.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRecuperarContrasenia.setBounds(80, 160, 180, 25);

        lblIdioma = new JLabel();
        lblIdioma.setBounds(30, 200, 80, 25);

        comboIdioma = new JComboBox<>();
        comboIdioma.setBounds(120, 200, 180, 25);
        comboIdioma.addItem("Español");
        comboIdioma.addItem("Inglés");
        comboIdioma.addItem("Francés");

        // 🔁 Listener para cambiar idioma en tiempo real
        comboIdioma.addActionListener(e -> {
            Locale nuevo = obtenerLocaleSeleccionado();
            mensajes.setLenguaje(nuevo.getLanguage(), nuevo.getCountry());
            actualizarTextos(mensajes);
        });

        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(lblContrasenia);
        panelPrincipal.add(txtContrasenia);
        panelPrincipal.add(btnLogin);
        panelPrincipal.add(btnRegistrarse);
        panelPrincipal.add(btnRecuperarContrasenia);
        panelPrincipal.add(lblIdioma);
        panelPrincipal.add(comboIdioma);

        // Traducir al iniciar
        actualizarTextos(mensajes);
    }

    public Locale obtenerLocaleSeleccionado() {
        String seleccionado = (String) comboIdioma.getSelectedItem();
        switch (seleccionado) {
            case "Inglés": return new Locale("en", "US");
            case "Francés": return new Locale("fr", "FR");
            default: return new Locale("es", "EC");
        }
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

    public JComboBox<String> getComboIdioma() {
        return comboIdioma;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("login.titulo"));
        lblUsuario.setText(mensajes.get("login.usuario"));
        lblContrasenia.setText(mensajes.get("login.contrasenia"));
        lblIdioma.setText(mensajes.get("login.idioma"));
        btnLogin.setText(mensajes.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajes.get("login.boton.registrarse"));
        btnRecuperarContrasenia.setText(mensajes.get("login.boton.recuperar"));
    }
}
