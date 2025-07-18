package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.IconUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Locale;

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

    public RecuperarContraseniaView(MensajeInternacionalizacionHandler mensajes, String preguntaSeguridad) {
        this.preguntaSeleccionada = preguntaSeguridad;
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();

        initComponents();
        actualizarTextos(mensajes);
    }

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

        // 🔹 NUEVO: Título elegante
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


    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("recuperar.titulo"));
        lblPregunta.setText(mensajes.get("recuperar.pregunta") + ":\n" + preguntaSeleccionada);
        btnVerificarRespuestas.setText(mensajes.get("recuperar.boton.verificar"));
        btnCambiarContrasenia.setText(mensajes.get("recuperar.boton.cambiar"));
        lblNuevaContrasenia.setText(mensajes.get("recuperar.nueva") + ":");
    }

    public String getRespuesta() {
        return txtRespuesta.getText().trim();
    }

    public String getNuevaContrasenia() {
        return new String(txtNuevaContrasenia.getPassword()).trim();
    }

    public JButton getBtnVerificarRespuestas() {
        return btnVerificarRespuestas;
    }

    public JButton getBtnCambiarContrasenia() {
        return btnCambiarContrasenia;
    }

    public void habilitarCambioContrasenia() {
        txtNuevaContrasenia.setEnabled(true);
        btnCambiarContrasenia.setEnabled(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public String getPreguntaSeleccionada() {
        return preguntaSeleccionada;
    }
}
