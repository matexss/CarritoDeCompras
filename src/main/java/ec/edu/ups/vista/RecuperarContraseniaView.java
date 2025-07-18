package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.IconUtil;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

import java.util.Locale;

/**
 * Vista para la recuperación de contraseña basada en preguntas de seguridad.
 * Permite al usuario verificar respuestas a preguntas y cambiar la contraseña si la respuesta es correcta.
 * Implementa el soporte para la internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class RecuperarContraseniaView extends JFrame implements ActualizableConIdioma {

    private JPanel panel1;
    private JTextField textPregunta1;
    private JTextField txtRespuesta;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnVerificarRespuestas;
    private JButton btnCambiarContrasenia;
    private String preguntaSeleccionada;
    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private JTextArea lblPregunta;
    private JLabel lblNuevaContrasenia;

    /**
     * Constructor para la vista de recuperación de contraseña.
     *
     * @param mensajes Manejador de mensajes internacionalizados.
     * @param preguntaSeguridad Pregunta de seguridad seleccionada para la verificación.
     */
    public RecuperarContraseniaView(MensajeInternacionalizacionHandler mensajes, String preguntaSeguridad) {
        this.preguntaSeleccionada = preguntaSeguridad;
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();

        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa los componentes gráficos de la interfaz.
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 340);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fondo = new Color(245, 222, 179);
        Color primario = new Color(100, 149, 237);
        Font fuenteLabel = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 13);

        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(fondo);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Título
        JLabel titulo = new JLabel("Recuperar Contraseña");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        panelPrincipal.add(titulo);

        lblPregunta = new JTextArea();
        lblPregunta.setWrapStyleWord(true);
        lblPregunta.setLineWrap(true);
        lblPregunta.setEditable(false);
        lblPregunta.setFocusable(false);
        lblPregunta.setBackground(fondo);
        lblPregunta.setFont(fuenteLabel);
        lblPregunta.setForeground(new Color(51, 51, 51));
        lblPregunta.setBorder(null);
        lblPregunta.setMaximumSize(new Dimension(1000, 60));

        txtRespuesta = new JTextField(20);
        txtRespuesta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        txtRespuesta.setFont(fuenteLabel);

        panelPrincipal.add(lblPregunta);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(txtRespuesta);
        panelPrincipal.add(Box.createVerticalStrut(15));

        btnVerificarRespuestas = new JButton(IconUtil.cargarIcono("search.png", 16, 16));
        btnVerificarRespuestas.setPreferredSize(new Dimension(180, 35));
        btnVerificarRespuestas.setBackground(primario);
        btnVerificarRespuestas.setForeground(Color.WHITE);
        btnVerificarRespuestas.setFocusPainted(false);
        btnVerificarRespuestas.setFont(fuenteBoton);

        JPanel panelVerificar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelVerificar.setBackground(fondo);
        panelVerificar.add(btnVerificarRespuestas);
        panelPrincipal.add(panelVerificar);

        panelPrincipal.add(Box.createVerticalStrut(20));

        JPanel panelCambio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCambio.setBackground(fondo);
        lblNuevaContrasenia = new JLabel();
        lblNuevaContrasenia.setFont(fuenteLabel);
        panelCambio.add(lblNuevaContrasenia);

        txtNuevaContrasenia = new JPasswordField(20);
        txtNuevaContrasenia.setEnabled(false);
        txtNuevaContrasenia.setFont(fuenteLabel);
        panelCambio.add(txtNuevaContrasenia);
        panelPrincipal.add(panelCambio);

        btnCambiarContrasenia = new JButton(IconUtil.cargarIcono("user-update.png", 16, 16));
        btnCambiarContrasenia.setEnabled(false);
        btnCambiarContrasenia.setBackground(new Color(60, 179, 113));
        btnCambiarContrasenia.setForeground(Color.WHITE);
        btnCambiarContrasenia.setFont(fuenteBoton);
        btnCambiarContrasenia.setFocusPainted(false);

        JPanel panelCambiar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCambiar.setBackground(fondo);
        panelCambiar.add(btnCambiarContrasenia);
        panelPrincipal.add(panelCambiar);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Actualiza los textos de la interfaz de acuerdo con el idioma seleccionado.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("recuperar.titulo"));
        lblPregunta.setText(mensajes.get("recuperar.pregunta") + ":\n" + preguntaSeleccionada);
        btnVerificarRespuestas.setText(mensajes.get("recuperar.boton.verificar"));
        btnCambiarContrasenia.setText(mensajes.get("recuperar.boton.cambiar"));
        lblNuevaContrasenia.setText(mensajes.get("recuperar.nueva") + ":");
    }

    /**
     * Devuelve la respuesta del usuario a la pregunta de seguridad.
     *
     * @return Respuesta ingresada por el usuario.
     */
    public String getRespuesta() {
        return txtRespuesta.getText().trim();
    }

    /**
     * Devuelve la nueva contraseña que el usuario desea establecer.
     *
     * @return Nueva contraseña.
     */
    public String getNuevaContrasenia() {
        return new String(txtNuevaContrasenia.getPassword()).trim();
    }

    /**
     * Botón para verificar si la respuesta a la pregunta de seguridad es correcta.
     *
     * @return El botón para verificar la respuesta.
     */
    public JButton getBtnVerificarRespuestas() {
        return btnVerificarRespuestas;
    }

    /**
     * Botón para cambiar la contraseña si la respuesta es correcta.
     *
     * @return El botón para cambiar la contraseña.
     */
    public JButton getBtnCambiarContrasenia() {
        return btnCambiarContrasenia;
    }

    /**
     * Habilita el campo para ingresar la nueva contraseña y el botón para cambiar la contraseña.
     */
    public void habilitarCambioContrasenia() {
        txtNuevaContrasenia.setEnabled(true);
        btnCambiarContrasenia.setEnabled(true);
    }

    /**
     * Muestra un mensaje en la pantalla.
     *
     * @param mensaje Mensaje que se mostrará al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Devuelve la pregunta seleccionada para la recuperación de contraseña.
     *
     * @return La pregunta seleccionada.
     */
    public String getPreguntaSeleccionada() {
        return preguntaSeleccionada;
    }
}
