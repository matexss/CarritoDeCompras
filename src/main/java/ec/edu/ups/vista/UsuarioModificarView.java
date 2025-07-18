package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

/**
 * Vista interna que permite modificar un usuario existente, cambiando su contraseña
 * y rol asignado. Incluye campos para búsqueda, edición y control visual con soporte de internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class UsuarioModificarView extends JInternalFrame implements ActualizableConIdioma {

    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JComboBox<Rol> cbxRoles;
    private JButton btnBuscar;
    private JButton btnModificar;

    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;

    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor de la vista. Inicializa componentes e idioma.
     *
     * @param mensajes Manejador de internacionalización.
     */
    public UsuarioModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa todos los componentes visuales de la ventana.
     */
    private void initComponents() {
        setSize(500, 300);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 232));

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(80, 20, 60));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        lblUsuario = new JLabel();
        lblContraseña = new JLabel();
        lblRol = new JLabel();

        txtUsuario = new JTextField();
        txtContraseña = new JTextField();
        cbxRoles = new JComboBox<>(Rol.values());

        btnBuscar = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        btnBuscar.setPreferredSize(new Dimension(160, 35));

        btnModificar = new JButton(IconUtil.cargarIcono("editar.png", 18, 18));
        btnModificar.setPreferredSize(new Dimension(160, 35));

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblContraseña, gbc);
        gbc.gridx = 1;
        formPanel.add(txtContraseña, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblRol, gbc);
        gbc.gridx = 1;
        formPanel.add(cbxRoles, gbc);

        JPanel botonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botonPanel.setOpaque(false);
        botonPanel.add(btnBuscar);
        botonPanel.add(btnModificar);

        add(formPanel, BorderLayout.CENTER);
        add(botonPanel, BorderLayout.SOUTH);

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Rol) {
                    Rol rol = (Rol) value;
                    setText(rol == Rol.ADMINISTRADOR
                            ? mensajes.get("global.rol.admin")
                            : mensajes.get("global.rol.user"));
                }
                return this;
            }
        });
    }

    /**
     * Actualiza los textos de la vista según el idioma actual.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("usuario.modificar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.modificar.titulo.app"));

        lblUsuario.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");

        txtUsuario.setToolTipText(mensajes.get("usuario.modificar.usuario"));
        txtContraseña.setToolTipText(mensajes.get("usuario.modificar.nuevaContraseña"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));

        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
    }

    /**
     * Devuelve el campo de texto con el nombre de usuario.
     *
     * @return JTextField del nombre de usuario.
     */
    public JTextField getTxtUsuario() { return txtUsuario; }

    /**
     * Devuelve el campo de texto de la contraseña.
     *
     * @return JTextField de la contraseña.
     */
    public JTextField getTxtContraseña() { return txtContraseña; }

    /**
     * Devuelve el combo de roles seleccionable.
     *
     * @return JComboBox con los roles disponibles.
     */
    public JComboBox<Rol> getCbxRoles() { return cbxRoles; }

    /**
     * Devuelve el botón de búsqueda.
     *
     * @return JButton para buscar un usuario.
     */
    public JButton getBtnBuscar() { return btnBuscar; }

    /**
     * Devuelve el botón para modificar al usuario.
     *
     * @return JButton para modificar.
     */
    public JButton getBtnModificar() { return btnModificar; }

    /**
     * Muestra un mensaje emergente con el texto indicado.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Limpia los campos de entrada y restablece el estado inicial de los botones.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        cbxRoles.setSelectedIndex(0);
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
        txtContraseña.setEnabled(false);
        cbxRoles.setEnabled(false);
        btnModificar.setEnabled(false);
    }
}
