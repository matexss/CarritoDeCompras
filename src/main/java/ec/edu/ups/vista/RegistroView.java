package ec.edu.ups.vista;

import ec.edu.ups.servicio.PreguntaSeguridadService;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.IconUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class RegistroView extends JFrame {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JComboBox<String> comboRol;
    private JComboBox<String> comboPregunta1;
    private JComboBox<String> comboPregunta2;
    private JComboBox<String> comboPregunta3;
    private JTextField txtRespuesta1;
    private JTextField txtRespuesta2;
    private JTextField txtRespuesta3;
    private JButton btnRegistrar;

    private MensajeInternacionalizacionHandler mensajes;

    public RegistroView(MensajeInternacionalizacionHandler mensajes, List<String> preguntas) {
        this.mensajes = mensajes;
        setTitle(mensajes.get("registro.titulo"));
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color fondo = new Color(255, 245, 230);
        Color inputBg = new Color(255, 255, 255);
        Color botonColor = new Color(186, 213, 255);
        Color bordeColor = new Color(173, 216, 230);
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondo);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        panel.add(createFieldPanel(mensajes.get("registro.usuario"), txtUsuario = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.contrasenia"), txtContrasenia = new JPasswordField(20)));
        panel.add(createFieldPanel(mensajes.get("registro.nombre"), txtNombre = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.fecha"), txtFechaNacimiento = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.correo"), txtCorreo = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.telefono"), txtTelefono = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createComboBoxPanel(mensajes.get("registro.rol"), comboRol = new JComboBox<>(new String[]{"USUARIO"})));

        comboPregunta1 = new JComboBox<>(preguntas.toArray(new String[0]));
        comboPregunta2 = new JComboBox<>(preguntas.toArray(new String[0]));
        comboPregunta3 = new JComboBox<>(preguntas.toArray(new String[0]));

        txtRespuesta1 = crearCampo(inputBg, fuente, bordeColor);
        txtRespuesta2 = crearCampo(inputBg, fuente, bordeColor);
        txtRespuesta3 = crearCampo(inputBg, fuente, bordeColor);

        panel.add(createComboBoxPanel(mensajes.get("registro.pregunta1"), comboPregunta1));
        panel.add(createFieldPanel(mensajes.get("registro.respuesta1"), txtRespuesta1));
        panel.add(createComboBoxPanel(mensajes.get("registro.pregunta2"), comboPregunta2));
        panel.add(createFieldPanel(mensajes.get("registro.respuesta2"), txtRespuesta2));
        panel.add(createComboBoxPanel(mensajes.get("registro.pregunta3"), comboPregunta3));
        panel.add(createFieldPanel(mensajes.get("registro.respuesta3"), txtRespuesta3));

        btnRegistrar = new JButton(mensajes.get("registro.btn.registrar"), IconUtil.cargarIcono("user-add.png", 18, 18));
        btnRegistrar.setBackground(botonColor);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(fuente);
        btnRegistrar.setBorder(new LineBorder(bordeColor));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(fondo);
        btnPanel.add(btnRegistrar);
        panel.add(btnPanel);

        setContentPane(panel);
    }

    private JTextField crearCampo(Color fondo, Font fuente, Color borde) {
        JTextField field = new JTextField(20);
        field.setBackground(fondo);
        field.setFont(fuente);
        field.setBorder(new LineBorder(borde));
        return field;
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(255, 245, 230));
        panel.add(new JLabel(labelText));
        panel.add(field);
        return panel;
    }

    private JPanel createComboBoxPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(255, 245, 230));
        panel.add(new JLabel(labelText));
        panel.add(comboBox);
        return panel;
    }

    public JTextField getTxtUsername() { return txtUsuario; }
    public JPasswordField getTxtContrasenia() { return txtContrasenia; }
    public JComboBox<String> getComboRol() { return comboRol; }
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public String getNombreCompleto() { return txtNombre.getText().trim(); }
    public String getFechaNacimiento() { return txtFechaNacimiento.getText().trim(); }
    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getTelefono() { return txtTelefono.getText().trim(); }

    public List<String> getPreguntasSeleccionadas() {
        List<String> preguntas = new ArrayList<>();
        preguntas.add((String) comboPregunta1.getSelectedItem());
        preguntas.add((String) comboPregunta2.getSelectedItem());
        preguntas.add((String) comboPregunta3.getSelectedItem());
        return preguntas;
    }

    public List<String> getRespuestasSeleccionadas() {
        List<String> respuestas = new ArrayList<>();
        respuestas.add(txtRespuesta1.getText().trim());
        respuestas.add(txtRespuesta2.getText().trim());
        respuestas.add(txtRespuesta3.getText().trim());
        return respuestas;
    }

    public boolean validarCampos() {
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0 ||
                getNombreCompleto().isEmpty() || getFechaNacimiento().isEmpty() ||
                getCorreo().isEmpty() || getTelefono().isEmpty()) {
            mostrarMensaje(mensajes.get("registro.error.campos.vacios"));
            return false;
        }

        if (!esFechaValida(getFechaNacimiento())) {
            mostrarMensaje(mensajes.get("registro.error.fecha"));
            return false;
        }

        if (txtRespuesta1.getText().isEmpty() || txtRespuesta2.getText().isEmpty() || txtRespuesta3.getText().isEmpty()) {
            mostrarMensaje(mensajes.get("registro.error.respuestas.vacias"));
            return false;
        }

        if (comboPregunta1.getSelectedItem().equals(comboPregunta2.getSelectedItem()) ||
                comboPregunta1.getSelectedItem().equals(comboPregunta3.getSelectedItem()) ||
                comboPregunta2.getSelectedItem().equals(comboPregunta3.getSelectedItem())) {
            mostrarMensaje(mensajes.get("registro.error.preguntas.duplicadas"));
            return false;
        }

        return true;
    }

    private boolean esFechaValida(String fecha) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
