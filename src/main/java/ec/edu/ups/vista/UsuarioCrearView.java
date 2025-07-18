package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.*;

import javax.swing.*;
import java.awt.*;

/**
 * Vista interna (JInternalFrame) que permite crear nuevos usuarios en el sistema.
 * Incluye campos para ingresar el nombre de usuario, contraseña y seleccionar un rol.
 *
 * Esta clase implementa {@link ActualizableConIdioma} para permitir la internacionalización dinámica.
 *
 * @author Mateo
 * @version 1.0
 */
public class UsuarioCrearView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JLabel lblTitulo, lblUsuarioA, lblContraseña, lblRol;
    private JTextField txtUsuario, txtContraseña;
    private JComboBox<Rol> cbxRoles;
    private JButton btnCrear;
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor que inicializa la vista con el manejador de internacionalización.
     *
     * @param mensajes Manejador de textos traducibles.
     */
    public UsuarioCrearView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        initComponents();
        actualizarTextos(mensajes);
        cargarRoles();
    }

    /**
     * Inicializa los componentes gráficos del formulario.
     */
    private void initComponents() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 228, 232));

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        lblTitulo.setForeground(new Color(80, 20, 60));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setOpaque(false);

        lblUsuarioA = new JLabel();
        lblContraseña = new JLabel();
        lblRol = new JLabel();

        txtUsuario = new JTextField();
        txtContraseña = new JTextField();
        cbxRoles = new JComboBox<>();

        btnCrear = new JButton(IconUtil.cargarIcono("user-add.png", 18, 18));

        formPanel.add(lblUsuarioA); formPanel.add(txtUsuario);
        formPanel.add(lblContraseña); formPanel.add(txtContraseña);
        formPanel.add(lblRol); formPanel.add(cbxRoles);
        formPanel.add(new JLabel()); formPanel.add(btnCrear);

        add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Actualiza los textos visibles de la interfaz en función del idioma seleccionado.
     *
     * @param mensajes Manejador de textos internacionalizados.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        setTitle(mensajes.get("usuario.crear.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.crear.titulo.app"));
        lblUsuarioA.setText(mensajes.get("global.usuario") + ":");
        lblContraseña.setText(mensajes.get("global.contraseña") + ":");
        lblRol.setText(mensajes.get("global.rol") + ":");

        txtUsuario.setToolTipText(mensajes.get("usuario.crear.nombre"));
        txtContraseña.setToolTipText(mensajes.get("usuario.crear.contraseña"));
        cbxRoles.setToolTipText(mensajes.get("usuario.crear.rol"));

        btnCrear.setText(mensajes.get("global.crear"));

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Rol) {
                    Rol rol = (Rol) value;
                    if (rol == Rol.ADMINISTRADOR) {
                        setText(mensajes.get("global.rol.admin"));
                    } else if (rol == Rol.USUARIO) {
                        setText(mensajes.get("global.rol.user"));
                    }
                }
                return this;
            }
        });
    }

    /**
     * Carga los roles disponibles (ADMINISTRADOR, USUARIO) en el combo box.
     */
    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            if (rol == Rol.ADMINISTRADOR || rol == Rol.USUARIO) {
                cbxRoles.addItem(rol);
            }
        }
    }

    // Getters públicos para acceso desde el controlador

    /** @return Campo de texto del nombre de usuario */
    public JTextField getTxtUsuario() { return txtUsuario; }

    /** @return Campo de texto de la contraseña */
    public JTextField getTxtContraseña() { return txtContraseña; }

    /** @return Botón para crear el usuario */
    public JButton getBtnCrear() { return btnCrear; }

    /** @return ComboBox de selección de roles */
    public JComboBox<Rol> getCbxRoles() { return cbxRoles; }

    /**
     * Limpia los campos del formulario, dejándolos vacíos o en el valor por defecto.
     */
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        cbxRoles.setSelectedIndex(0);
    }

    /**
     * Muestra un mensaje informativo al usuario usando un diálogo estándar.
     *
     * @param mensaje El texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
