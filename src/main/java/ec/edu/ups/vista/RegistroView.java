package ec.edu.ups.vista;

import ec.edu.ups.servicio.PreguntaSeguridadService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroView extends JFrame {

    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPasswordField txtContrasenia;
    private JComboBox<String> comboRol;
    private JButton btnRegistrar;



    private final List<String> preguntasSeleccionadas;
    private final List<JTextField> camposRespuestas;

    public RegistroView(PreguntaSeguridadService servicio) {
        setTitle("Registro de Usuario");
        Color fondo = new Color(215, 144, 70);
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        panel1.add(createFieldPanel("Usuario:", txtUsuario = new JTextField(20)));
        panel1.add(createFieldPanel("Contraseña:", txtContrasenia = new JPasswordField(20)));
        panel1.add(createComboBoxPanel("Rol:", comboRol = new JComboBox<>(new String[]{"USUARIO"})));

        preguntasSeleccionadas = servicio.obtenerPreguntasAleatorias(3);
        camposRespuestas = new ArrayList<>();

        for (String pregunta : preguntasSeleccionadas) {
            JTextField campo = new JTextField(20);
            panel1.add(createFieldPanel(pregunta, campo));
            camposRespuestas.add(campo);
        }

        btnRegistrar = new JButton("Registrar");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(btnRegistrar);
        panel1.add(btnPanel);

        setContentPane(panel1);
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

    public JTextField getTxtUsername() {
        return txtUsuario;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }

    public JComboBox<String> getComboRol() {
        return comboRol;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public List<String> getPreguntasSeguridad() {
        return preguntasSeleccionadas;
    }

    public List<String> getRespuestasSeguridad() {
        List<String> respuestas = new ArrayList<>();
        for (JTextField campo : camposRespuestas) {
            respuestas.add(campo.getText().trim());
        }
        return respuestas;
    }

    public boolean validarCampos() {
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getPassword().length == 0) {
            mostrarMensaje("Usuario y contraseña son obligatorios.");
            return false;
        }
        for (JTextField campo : camposRespuestas) {
            if (campo.getText().trim().isEmpty()) {
                mostrarMensaje("Debes responder todas las preguntas de seguridad.");
                return false;
            }
        }
        return true;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
