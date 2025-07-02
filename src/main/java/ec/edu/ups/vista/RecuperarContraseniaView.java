package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class RecuperarContraseniaView extends JFrame {
    private JPanel panel1;
    private JTextField txtPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JButton btnVerificarRespuestas;
    private JButton btnCambiarContrasenia;
    private JPasswordField txtNuevaContrasenia;

    public RecuperarContraseniaView(List<String> preguntas) {
        setTitle("Recuperación de Contraseña");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        Color fondo = new Color(215, 144, 70);
        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());

        // Panel reservado aunque no se use directamente
        panel1 = new JPanel();
        panel1.setVisible(false); // Oculto de momento
        add(panel1, BorderLayout.NORTH);

        // Panel principal con BoxLayout vertical
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(fondo);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Sección de preguntas
        panelPrincipal.add(crearPanelPregunta(preguntas.get(0), txtPregunta1 = new JTextField(20), fondo));
        panelPrincipal.add(Box.createVerticalStrut(12));
        panelPrincipal.add(crearPanelPregunta(preguntas.get(1), txtPregunta2 = new JTextField(20), fondo));
        panelPrincipal.add(Box.createVerticalStrut(12));
        panelPrincipal.add(crearPanelPregunta(preguntas.get(2), txtPregunta3 = new JTextField(20), fondo));
        panelPrincipal.add(Box.createVerticalStrut(20));

        // Botón verificar
        btnVerificarRespuestas = new JButton("Verificar Respuestas");
        btnVerificarRespuestas.setPreferredSize(new Dimension(180, 35));
        JPanel panelBotonVerificar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonVerificar.setBackground(fondo);
        panelBotonVerificar.add(btnVerificarRespuestas);
        panelPrincipal.add(panelBotonVerificar);

        panelPrincipal.add(Box.createVerticalStrut(25));

        // Sección nueva contraseña
        JPanel panelCambio = new JPanel(new GridBagLayout());
        panelCambio.setBackground(fondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCambio.add(new JLabel("Nueva Contraseña:"), gbc);

        gbc.gridx = 1;
        txtNuevaContrasenia = new JPasswordField(15);
        panelCambio.add(txtNuevaContrasenia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        btnCambiarContrasenia = new JButton("Cambiar Contraseña");
        btnCambiarContrasenia.setPreferredSize(new Dimension(180, 35));
        btnCambiarContrasenia.setEnabled(false);
        panelCambio.add(btnCambiarContrasenia, gbc);

        panelPrincipal.add(panelCambio);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JPanel crearPanelPregunta(String pregunta, JTextField textField, Color fondo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(fondo);

        // JLabel largo usando JTextArea no editable para múltiples líneas
        JTextArea label = new JTextArea("Pregunta: " + pregunta);
        label.setWrapStyleWord(true);
        label.setLineWrap(true);
        label.setEditable(false);
        label.setFocusable(false);
        label.setBackground(fondo);
        label.setFont(new JLabel().getFont());
        label.setMaximumSize(new Dimension(1000, 40));
        label.setBorder(null);

        // Ajustar tamaño del textfield
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);

        return panel;
    }

    // Getters
    public JTextField getTxtPregunta1() { return txtPregunta1; }
    public JTextField getTxtPregunta2() { return txtPregunta2; }
    public JTextField getTxtPregunta3() { return txtPregunta3; }
    public JButton getBtnVerificarRespuestas() { return btnVerificarRespuestas; }
    public JButton getBtnCambiarContrasenia() { return btnCambiarContrasenia; }
    public JPasswordField getTxtNuevaContrasenia() { return txtNuevaContrasenia; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Test
    public static void main(String[] args) {
        List<String> preguntas = List.of(
                "¿Cuál es el nombre de tu primer mascota?",
                "¿Cuál es el nombre de tu héroe favorito y por qué lo admiras?",
                "¿Cuál es la primera comida que aprendiste a cocinar cuando eras niño?"
        );
        SwingUtilities.invokeLater(() -> {
            RecuperarContraseniaView vista = new RecuperarContraseniaView(preguntas);
            vista.setVisible(true);
        });
    }
}
