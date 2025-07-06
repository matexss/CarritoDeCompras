package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RecuperarContraseniaView extends JFrame {
    private JPanel panel1;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JTextField txtRespuesta;
    private JPasswordField txtNuevaContrasenia;
    private JButton btnVerificarRespuestas;
    private JButton btnCambiarContrasenia;
    private String preguntaSeleccionada;

    public RecuperarContraseniaView(String preguntaSeguridad) {
        this.preguntaSeleccionada = preguntaSeguridad;

        setTitle("Recuperación de Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        Color fondo = new Color(215, 144, 70);
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(fondo);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Mostrar la pregunta
        JTextArea lblPregunta = new JTextArea("Pregunta de seguridad:\n" + preguntaSeguridad);
        lblPregunta.setWrapStyleWord(true);
        lblPregunta.setLineWrap(true);
        lblPregunta.setEditable(false);
        lblPregunta.setFocusable(false);
        lblPregunta.setBackground(fondo);
        lblPregunta.setFont(new JLabel().getFont());
        lblPregunta.setMaximumSize(new Dimension(1000, 60));
        lblPregunta.setBorder(null);

        txtRespuesta = new JTextField(20);
        txtRespuesta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        panelPrincipal.add(lblPregunta);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(txtRespuesta);
        panelPrincipal.add(Box.createVerticalStrut(15));

        // Botón verificar
        btnVerificarRespuestas = new JButton("Verificar Respuesta");
        btnVerificarRespuestas.setPreferredSize(new Dimension(180, 35));
        JPanel panelVerificar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelVerificar.setBackground(fondo);
        panelVerificar.add(btnVerificarRespuestas);
        panelPrincipal.add(panelVerificar);

        panelPrincipal.add(Box.createVerticalStrut(20));

        // Nueva contraseña
        JPanel panelCambio = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCambio.setBackground(fondo);
        panelCambio.add(new JLabel("Nueva Contraseña:"));
        txtNuevaContrasenia = new JPasswordField(20);
        txtNuevaContrasenia.setEnabled(false);
        panelCambio.add(txtNuevaContrasenia);

        panelPrincipal.add(panelCambio);

        // Botón cambiar contraseña
        btnCambiarContrasenia = new JButton("Cambiar Contraseña");
        btnCambiarContrasenia.setEnabled(false);
        JPanel panelCambiar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCambiar.setBackground(fondo);
        panelCambiar.add(btnCambiarContrasenia);
        panelPrincipal.add(panelCambiar);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    // Getters necesarios
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

    // Test opcional
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecuperarContraseniaView vista = new RecuperarContraseniaView("¿Cuál es tu película favorita?");
            vista.setVisible(true);
        });
    }
}