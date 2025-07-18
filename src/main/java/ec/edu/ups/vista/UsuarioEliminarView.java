package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Vista interna (JInternalFrame) que permite eliminar un usuario del sistema.
 *
 * Esta clase proporciona una interfaz gráfica para ingresar el nombre de usuario
 * a eliminar y ejecutar la acción correspondiente. Además, soporta internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class UsuarioEliminarView extends JInternalFrame implements ActualizableConIdioma {

    private JLabel lblTitulo, lblUsuario;
    private JTextField txtUsuario;
    private JButton btnEliminar, button1;
    private JTextField textField1;
    private JTextField textField2;
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor que inicializa la vista con el manejador de mensajes internacionalizados.
     *
     * @param mensajes Manejador de internacionalización para traducción de textos.
     */
    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa todos los componentes gráficos de la ventana.
     */
    private void initComponents() {
        setSize(400, 220);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 232));

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(80, 20, 60));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelCentro.setOpaque(false);

        lblUsuario = new JLabel();
        txtUsuario = new JTextField();

        btnEliminar = new JButton("Eliminar", IconUtil.cargarIcono("user-delete.png", 18, 18));

        panelCentro.add(lblUsuario);
        panelCentro.add(txtUsuario);
        panelCentro.add(new JLabel());
        panelCentro.add(btnEliminar);

        add(panelCentro, BorderLayout.CENTER);
    }

    /**
     * Actualiza los textos de los componentes con base en el idioma seleccionado.
     *
     * @param mensajes Manejador de internacionalización con las traducciones activas.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("usuario.eliminar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.eliminar.titulo.app"));
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));
    }

    /**
     * Retorna el campo de texto donde se ingresa el nombre de usuario a eliminar.
     *
     * @return JTextField del nombre de usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Retorna el botón encargado de ejecutar la acción de eliminación.
     *
     * @return JButton para eliminar el usuario.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Muestra un mensaje al usuario en forma de cuadro de diálogo.
     *
     * @param mensaje Texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia el campo de texto, dejándolo vacío.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
    }
}
