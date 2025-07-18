package ec.edu.ups.vista;

import ec.edu.ups.modelo.Pregunta;
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

/**
 * Vista para el registro de usuario, donde el usuario puede ingresar su información personal,
 * responder preguntas de seguridad, y elegir su rol.
 *
 * Esta vista incluye validaciones de campos y garantiza que la información sea válida antes de registrarse.
 *
 * @author Mateo
 * @version 1.0
 */
public class RegistroView extends JFrame {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JTextField txtNombre;
    private JTextField txtFechaNacimiento;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JComboBox<String> comboRol;
    private JComboBox<Pregunta> comboPregunta1;
    private JComboBox<Pregunta> comboPregunta2;
    private JComboBox<Pregunta> comboPregunta3;
    private JTextField txtRespuesta1;
    private JTextField txtRespuesta2;
    private JTextField txtRespuesta3;
    private JButton btnRegistrar;
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista de registro de usuario.
     * Inicializa los componentes gráficos y configura la interfaz según el idioma seleccionado.
     *
     * @param mensajes Manejador de internacionalización para actualizar los textos en la interfaz.
     * @param preguntas Lista de preguntas de seguridad disponibles para el registro.
     */
    public RegistroView(MensajeInternacionalizacionHandler mensajes, List<Pregunta> preguntas) {
        this.mensajes = mensajes;
        setTitle(mensajes.get("registro.titulo"));
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        Color fondo1 = new Color(255, 228, 232);
        Color fondo2 = new Color(215, 159, 175);
        Color inputBg = Color.WHITE;
        Color botonColor = new Color(186, 213, 255);
        Color bordeColor = new Color(173, 216, 230);
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);

        // Título del formulario
        JLabel titulo = new JLabel("Registro de Usuario");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(10, 10, 20, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 30, 10, 30));
        panel.add(titulo);

        panel.add(createFieldPanel(mensajes.get("registro.usuario"), txtUsuario = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.contrasenia"), txtContrasenia = new JPasswordField(20)));
        panel.add(createFieldPanel(mensajes.get("registro.nombre"), txtNombre = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.fecha"), txtFechaNacimiento = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.correo"), txtCorreo = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createFieldPanel(mensajes.get("registro.telefono"), txtTelefono = crearCampo(inputBg, fuente, bordeColor)));
        panel.add(createComboBoxPanel(mensajes.get("registro.rol"), comboRol = new JComboBox<>(new String[]{"USUARIO"})));

        comboPregunta1 = new JComboBox<>(preguntas.toArray(new Pregunta[0]));
        comboPregunta2 = new JComboBox<>(preguntas.toArray(new Pregunta[0]));
        comboPregunta3 = new JComboBox<>(preguntas.toArray(new Pregunta[0]));

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
        btnPanel.setOpaque(false);
        btnPanel.add(btnRegistrar);
        panel.add(btnPanel);

        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, fondo1, 0, getHeight(), fondo2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(panel, BorderLayout.CENTER);
        setContentPane(fondoPanel);
    }

    /**
     * Crea un campo de texto con el formato especificado.
     *
     * @param fondo Color de fondo del campo.
     * @param fuente Fuente del texto en el campo.
     * @param borde Color del borde del campo.
     * @return El campo de texto creado.
     */
    private JTextField crearCampo(Color fondo, Font fuente, Color borde) {
        JTextField field = new JTextField(25);
        field.setBackground(fondo);
        field.setFont(fuente);
        field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(borde, 1), new EmptyBorder(5, 8, 5, 8)));
        return field;
    }

    /**
     * Crea un panel con un campo de texto.
     *
     * @param labelText El texto que se mostrará en el label.
     * @param field El campo de texto.
     * @return El panel con el label y el campo de texto.
     */
    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(new JLabel(labelText));
        panel.add(field);
        return panel;
    }

    /**
     * Crea un panel con un JComboBox.
     *
     * @param labelText El texto que se mostrará en el label.
     * @param comboBox El JComboBox.
     * @return El panel con el label y el combo box.
     */
    private JPanel createComboBoxPanel(String labelText, JComboBox<?> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.add(new JLabel(labelText));
        panel.add(comboBox);
        return panel;
    }

    /**
     * Obtiene el nombre de usuario ingresado en el campo de texto.
     *
     * @return El nombre de usuario.
     */
    public JTextField getTxtUsername() { return txtUsuario; }

    /**
     * Obtiene la contraseña ingresada en el campo de texto.
     *
     * @return La contraseña ingresada.
     */
    public JPasswordField getTxtContrasenia() { return txtContrasenia; }

    /**
     * Obtiene el combo box que contiene el rol seleccionado por el usuario.
     *
     * @return El combo box de rol.
     */
    public JComboBox<String> getComboRol() { return comboRol; }

    /**
     * Obtiene el botón de registrar.
     *
     * @return El botón de registrar.
     */
    public JButton getBtnRegistrar() { return btnRegistrar; }

    /**
     * Obtiene el nombre completo ingresado en el campo de texto.
     *
     * @return El nombre completo.
     */
    public String getNombreCompleto() { return txtNombre.getText().trim(); }

    /**
     * Obtiene la fecha de nacimiento ingresada en el campo de texto.
     *
     * @return La fecha de nacimiento.
     */
    public String getFechaNacimiento() { return txtFechaNacimiento.getText().trim(); }

    /**
     * Obtiene el correo ingresado en el campo de texto.
     *
     * @return El correo.
     */
    public String getCorreo() { return txtCorreo.getText().trim(); }

    /**
     * Obtiene el teléfono ingresado en el campo de texto.
     *
     * @return El teléfono.
     */
    public String getTelefono() { return txtTelefono.getText().trim(); }

    /**
     * Obtiene las preguntas de seguridad seleccionadas por el usuario.
     *
     * @return Una lista de preguntas seleccionadas.
     */
    public List<Pregunta> getPreguntasSeleccionadas() {
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add((Pregunta) comboPregunta1.getSelectedItem());
        preguntas.add((Pregunta) comboPregunta2.getSelectedItem());
        preguntas.add((Pregunta) comboPregunta3.getSelectedItem());
        return preguntas;
    }

    /**
     * Obtiene las respuestas a las preguntas de seguridad.
     *
     * @return Una lista de respuestas.
     */
    public List<String> getRespuestasSeleccionadas() {
        List<String> respuestas = new ArrayList<>();
        respuestas.add(txtRespuesta1.getText().trim());
        respuestas.add(txtRespuesta2.getText().trim());
        respuestas.add(txtRespuesta3.getText().trim());
        return respuestas;
    }

    /**
     * Valida los campos del formulario.
     *
     * @return Verdadero si todos los campos son válidos, falso en caso contrario.
     */
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

    /**
     * Verifica si la fecha ingresada es válida.
     *
     * @param fecha La fecha a verificar.
     * @return Verdadero si la fecha es válida, falso en caso contrario.
     */
    private boolean esFechaValida(String fecha) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Muestra un mensaje al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
