package ec.edu.ups.vista;

import ec.edu.ups.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * Vista gráfica para permitir que el usuario actualice su información personal,
 * como nombre, fecha de nacimiento, correo, teléfono y contraseña.
 *
 * Esta clase implementa la interfaz {@code ActualizableConIdioma} para permitir
 * la actualización dinámica de los textos en distintos idiomas.
 *
 * @author Mateo
 * @version 1.0
 */
public class UsuarioActualizarView extends JFrame implements ActualizableConIdioma {

    private JPanel panel1;
    private JTextField txtNombre, txtFecha, txtCorreo, txtTelefono;
    private JPasswordField txtNueva;
    private JButton btnGuardar;
    private JButton button1; // Sin uso, posible refactor futuro
    private final MensajeInternacionalizacionHandler mensajes;

    private JLabel lNombre, lFecha, lCorreo, lTel, lNueva;

    /**
     * Constructor de la vista que recibe el manejador de internacionalización.
     *
     * @param m Objeto encargado de manejar los textos en distintos idiomas.
     */
    public UsuarioActualizarView(MensajeInternacionalizacionHandler m) {
        mensajes = m;
        init();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa todos los componentes gráficos de la ventana.
     */
    private void init() {
        setSize(500, 350);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 228, 232));
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Actualizar Usuario", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        form.setOpaque(false);

        lNombre = new JLabel(); txtNombre = new JTextField();
        lFecha  = new JLabel(); txtFecha = new JTextField();
        lCorreo = new JLabel(); txtCorreo = new JTextField();
        lTel    = new JLabel(); txtTelefono = new JTextField();
        lNueva  = new JLabel(); txtNueva = new JPasswordField();

        btnGuardar = new JButton(IconUtil.cargarIcono("guardar.png", 18, 18));

        form.add(lNombre); form.add(txtNombre);
        form.add(lFecha);  form.add(txtFecha);
        form.add(lCorreo); form.add(txtCorreo);
        form.add(lTel);    form.add(txtTelefono);
        form.add(lNueva);  form.add(txtNueva);
        form.add(new JLabel()); form.add(btnGuardar);

        add(form, BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Actualiza los textos visibles en la interfaz según el idioma proporcionado
     * por el manejador de internacionalización.
     *
     * @param mensajes Manejador de textos internacionalizados.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("usuario.actualizar.titulo"));
        lNombre.setText(mensajes.get("usuario.actualizar.nombre") + ":");
        lFecha.setText(mensajes.get("usuario.actualizar.fecha.nacimiento") + ":");
        lCorreo.setText(mensajes.get("usuario.actualizar.correo") + ":");
        lTel.setText(mensajes.get("usuario.actualizar.telefono") + ":");
        lNueva.setText(mensajes.get("usuario.actualizar.nueva.contrasenia") + ":");
        btnGuardar.setText(mensajes.get("usuario.actualizar.btn.guardar"));
    }
}
