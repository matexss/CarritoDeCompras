package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Locale;

/**
 * Vista principal de inicio de sesión del sistema.
 * Permite al usuario ingresar sus credenciales, registrarse o recuperar su contraseña.
 * También incluye selección de idioma con soporte de internacionalización dinámica.
 *
 * @author Mateo
 * @version 1.0
 */
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

    /**
     * Constructor de la vista Login.
     *
     * @param mensajes manejador de internacionalización.
     */
    public LoginView(MensajeInternacionalizacionHandler mensajes) {
        this.mensajes = mensajes;

        setTitle("Login");
        setSize(380, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel titulo = new JLabel("Login");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(10, 10, 20, 10));

        setLayout(new BorderLayout());

        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 228, 232);
                Color color2 = new Color(215, 159, 175);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondoPanel.setLayout(null);
        setContentPane(fondoPanel);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBounds(0, 0, 350, 300);
        panelPrincipal.setOpaque(false);
        panelPrincipal.add(titulo);
        fondoPanel.add(panelPrincipal);

        lblUsuario = new JLabel();
        lblUsuario.setBounds(30, 30, 80, 25);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(120, 30, 180, 25);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        lblContrasenia = new JLabel();
        lblContrasenia.setBounds(30, 70, 80, 25);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(120, 70, 180, 25);
        txtContrasenia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        btnLogin = new JButton(IconUtil.cargarIcono("logout.png", 18, 18));
        btnLogin.setBounds(30, 120, 130, 30);
        btnLogin.setFocusPainted(false);
        btnLogin.setBackground(new Color(255, 220, 220));
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnRegistrarse = new JButton(IconUtil.cargarIcono("user-add.png", 18, 18));
        btnRegistrarse.setBounds(170, 120, 130, 30);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setBackground(new Color(255, 220, 220));
        btnRegistrarse.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnRecuperarContrasenia = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        btnRecuperarContrasenia.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRecuperarContrasenia.setBounds(80, 160, 180, 25);
        btnRecuperarContrasenia.setFocusPainted(false);
        btnRecuperarContrasenia.setBackground(new Color(255, 220, 220));
        btnRecuperarContrasenia.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        lblIdioma = new JLabel();
        lblIdioma.setBounds(30, 200, 80, 25);

        comboIdioma = new JComboBox<>();
        comboIdioma.setBounds(120, 200, 180, 25);
        comboIdioma.addItem("Español");
        comboIdioma.addItem("Inglés");
        comboIdioma.addItem("Francés");

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

        actualizarTextos(mensajes);
    }

    /**
     * Obtiene el idioma seleccionado en el ComboBox.
     *
     * @return Locale correspondiente al idioma seleccionado.
     */
    public Locale obtenerLocaleSeleccionado() {
        String seleccionado = (String) comboIdioma.getSelectedItem();
        switch (seleccionado) {
            case "Inglés": return new Locale("en", "US");
            case "Francés": return new Locale("fr", "FR");
            default: return new Locale("es", "EC");
        }
    }

    /**
     * Devuelve el campo de texto del usuario.
     *
     * @return JTextField del nombre de usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Devuelve el campo de texto de la contraseña.
     *
     * @return JPasswordField del usuario.
     */
    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    /**
     * Devuelve el botón de inicio de sesión.
     *
     * @return JButton para login.
     */
    public JButton getBtnLogin() {
        return btnLogin;
    }

    /**
     * Devuelve el botón para registrarse.
     *
     * @return JButton de registro.
     */
    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    /**
     * Devuelve el botón para recuperar contraseña.
     *
     * @return JButton de recuperación de contraseña.
     */
    public JButton getBtnRecuperarContrasenia() {
        return btnRecuperarContrasenia;
    }

    /**
     * Devuelve el JComboBox para cambiar el idioma.
     *
     * @return JComboBox de idiomas.
     */
    public JComboBox<String> getComboIdioma() {
        return comboIdioma;
    }

    /**
     * Muestra un mensaje al usuario en una ventana de diálogo.
     *
     * @param mensaje el mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     *
     * @param mensajes manejador de internacionalización.
     */
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
