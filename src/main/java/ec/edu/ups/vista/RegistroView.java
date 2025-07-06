package ec.edu.ups.vista;

import ec.edu.ups.servicio.PreguntaSeguridadService;

import javax.swing.*;
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

    public RegistroView(PreguntaSeguridadService servicio) {
        setTitle("Registro de Usuario");
        Color fondo = new Color(215, 144, 70);
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(createFieldPanel("Usuario:", txtUsuario = new JTextField(20)));
        panel.add(createFieldPanel("Contraseña:", txtContrasenia = new JPasswordField(20)));
        panel.add(createFieldPanel("Nombre completo:", txtNombre = new JTextField(20)));
        panel.add(createFieldPanel("Fecha de nacimiento:", txtFechaNacimiento = new JTextField(20)));
        panel.add(createFieldPanel("Correo:", txtCorreo = new JTextField(20)));
        panel.add(createFieldPanel("Teléfono:", txtTelefono = new JTextField(20)));
        panel.add(createComboBoxPanel("Rol:", comboRol = new JComboBox<>(new String[]{"USUARIO"})));

        List<String> preguntas = servicio.obtenerTodasLasPreguntas();
        comboPregunta1 = new JComboBox<>(preguntas.toArray(new String[0]));
        comboPregunta2 = new JComboBox<>(preguntas.toArray(new String[0]));
        comboPregunta3 = new JComboBox<>(preguntas.toArray(new String[0]));

        txtRespuesta1 = new JTextField(20);
        txtRespuesta2 = new JTextField(20);
        txtRespuesta3 = new JTextField(20);

        panel.add(createComboBoxPanel("Pregunta 1:", comboPregunta1));
        panel.add(createFieldPanel("Respuesta 1:", txtRespuesta1));

        panel.add(createComboBoxPanel("Pregunta 2:", comboPregunta2));
        panel.add(createFieldPanel("Respuesta 2:", txtRespuesta2));

        panel.add(createComboBoxPanel("Pregunta 3:", comboPregunta3));
        panel.add(createFieldPanel("Respuesta 3:", txtRespuesta3));

        btnRegistrar = new JButton("Registrar");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnRegistrar);
        panel.add(btnPanel);

        setContentPane(panel);
    }

    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(field);
        return panel;
    }

    private JPanel createComboBoxPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(comboBox);
        return panel;
    }

    // Getters públicos
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
            mostrarMensaje("Todos los campos deben estar llenos.");
            return false;
        }

        // Validar formato de fecha
        if (!esFechaValida(getFechaNacimiento())) {
            mostrarMensaje("La fecha debe tener el formato dd/MM/yyyy.");
            return false;
        }

        if (txtRespuesta1.getText().isEmpty() || txtRespuesta2.getText().isEmpty() || txtRespuesta3.getText().isEmpty()) {
            mostrarMensaje("Debes responder todas las preguntas.");
            return false;
        }

        if (comboPregunta1.getSelectedItem().equals(comboPregunta2.getSelectedItem()) ||
                comboPregunta1.getSelectedItem().equals(comboPregunta3.getSelectedItem()) ||
                comboPregunta2.getSelectedItem().equals(comboPregunta3.getSelectedItem())) {
            mostrarMensaje("Las preguntas de seguridad deben ser distintas.");
            return false;
        }

        return true;
    }
    private boolean esFechaValida(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // No permite fechas inválidas como 32/13/2000
        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
