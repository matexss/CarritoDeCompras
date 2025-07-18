package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Vista interna que permite a los usuarios modificar su propio nombre de usuario
 * y contraseña, manteniendo el campo actual de usuario como solo lectura.
 * Compatible con internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class UsuarioModificarMisView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JPanel panel1;
    private JLabel lblUsuario, lblContraseña, lblTitulo, lblNuevoUser;
    private JTextField txtUsuario, txtContraseña, txtNuevoUser;
    private JButton btnModificar;
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista.
     *
     * @param mensajes Manejador de textos internacionalizados.
     */
    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;

        inicializarComponentes();
        setContentPane(panelPrincipal);
        setSize(500, 300);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        txtUsuario.setEditable(false);
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa los componentes visuales y organiza el layout de la ventana.
     */
    private void inicializarComponentes() {
        Color fondo = new Color(255, 228, 232);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(fondo);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(fondo);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonPanel.setBackground(fondo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTitulo = new JLabel();
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(80, 20, 60));

        lblUsuario = new JLabel();
        txtUsuario = new JTextField();

        lblNuevoUser = new JLabel();
        txtNuevoUser = new JTextField();

        lblContraseña = new JLabel();
        txtContraseña = new JTextField();

        btnModificar = new JButton(IconUtil.cargarIcono("user-update.png", 18, 18));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblNuevoUser, gbc);
        gbc.gridx = 1;
        formPanel.add(txtNuevoUser, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblContraseña, gbc);
        gbc.gridx = 1;
        formPanel.add(txtContraseña, gbc);

        botonPanel.add(btnModificar);

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(formPanel, BorderLayout.CENTER);
        panelPrincipal.add(botonPanel, BorderLayout.SOUTH);
    }

    /**
     * Actualiza los textos visibles en la interfaz de acuerdo con el idioma seleccionado.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("menu.usuario.modificarMis"));
        lblTitulo.setText(mensajes.get("menu.usuario.modificarMis"));
        lblNuevoUser.setText(mensajes.get("global.nuevoUsuario") + ":");
        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");

        txtNuevoUser.setToolTipText(mensajes.get("usuario.modificar.usuario"));
        txtContraseña.setToolTipText(mensajes.get("usuario.modificar.nuevaContraseña"));

        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    /**
     * Obtiene el campo de texto con el nuevo nombre de usuario.
     *
     * @return JTextField con el nuevo nombre de usuario.
     */
    public JTextField getTxtNuevoUser() {
        return txtNuevoUser;
    }

    /**
     * Obtiene el campo de texto para la nueva contraseña.
     *
     * @return JTextField con la nueva contraseña.
     */
    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    /**
     * Obtiene el botón para ejecutar la acción de modificación.
     *
     * @return JButton de modificar.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * Obtiene el campo de texto que muestra el usuario actual (no editable).
     *
     * @return JTextField con el nombre de usuario actual.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Muestra un mensaje emergente informativo.
     *
     * @param mensaje Mensaje que se desea mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia los campos de entrada de la vista.
     */
    public void limpiarCampos() {
        txtContraseña.setText("");
        txtNuevoUser.setText("");
    }
}
