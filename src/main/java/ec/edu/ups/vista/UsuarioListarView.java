package ec.edu.ups.vista;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista interna que permite listar usuarios registrados en el sistema.
 * Incluye funciones para buscar usuarios por nombre y mostrar su rol.
 * Soporta internacionalización mediante el uso de {@link MensajeInternacionalizacionHandler}.
 *
 * @author Mateo
 * @version 1.0
 */
public class UsuarioListarView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JLabel lblListado;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JLabel lblTitulo;
    private JTextField textField1;
    private DefaultTableModel tableModel;
    private Locale locale;
    private List<Usuario> listaActual;
    private MensajeInternacionalizacionHandler mensajes;

    /**
     * Constructor que inicializa la vista y sus componentes.
     *
     * @param mensajes Manejador de internacionalización para traducir textos.
     */
    public UsuarioListarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        initComponents();
        configurarTabla();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa y configura los componentes gráficos de la ventana.
     */
    private void initComponents() {
        setSize(600, 400);
        Color fondo = new Color(255, 228, 232);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);

        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(fondo);
        setContentPane(panelPrincipal);

        lblTitulo = new JLabel("", SwingConstants.CENTER);
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(new Color(80, 20, 60));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(fondo);

        JPanel panelSuperior = new JPanel(new FlowLayout());
        panelSuperior.setBackground(fondo);

        btnListar = new JButton(IconUtil.cargarIcono("user-list.png", 18, 18));
        btnBuscar = new JButton(IconUtil.cargarIcono("search.png", 18, 18));
        lblListado = new JLabel();
        txtUsuario = new JTextField(15);

        panelSuperior.add(lblListado);
        panelSuperior.add(txtUsuario);
        panelSuperior.add(btnBuscar);

        panelCentro.add(panelSuperior, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tblUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblUsuarios);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);
    }

    /**
     * Configura el modelo de la tabla utilizada para mostrar los usuarios.
     */
    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblUsuarios.setModel(tableModel);
    }

    /**
     * Actualiza los textos de los componentes con base en el idioma seleccionado.
     * También actualiza los encabezados de la tabla.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        this.locale = mensajes.getLocale();
        setTitle(mensajes.get("usuario.listar.titulo.app"));
        lblTitulo.setText(mensajes.get("usuario.listar.titulo.app"));
        lblListado.setText(mensajes.get("global.usuario") + ":");
        btnListar.setText(mensajes.get("menu.usuario.listar"));
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        txtUsuario.setToolTipText(mensajes.get("mensaje.usuario.buscar.vacio"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensajes.get("global.usuario"),
                mensajes.get("global.rol")
        });
        mostrarUsuarios(listaActual);
    }

    /**
     * Muestra en la tabla una lista de usuarios.
     *
     * @param usuarios Lista de usuarios a mostrar.
     */
    public void mostrarUsuarios(List<Usuario> usuarios) {
        this.listaActual = usuarios;
        tableModel.setRowCount(0);
        if (usuarios == null) return;
        for (Usuario usuario : usuarios) {
            String rolTraducido = usuario.getRol() == Rol.ADMINISTRADOR
                    ? mensajes.get("global.rol.admin")
                    : mensajes.get("global.rol.user");

            tableModel.addRow(new Object[]{
                    usuario.getUsername(),
                    rolTraducido
            });
        }
    }

    /**
     * Retorna el botón utilizado para listar todos los usuarios.
     *
     * @return JButton para listar usuarios.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Retorna el botón utilizado para buscar un usuario.
     *
     * @return JButton para buscar usuarios.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Retorna el campo de texto utilizado para ingresar el nombre del usuario a buscar.
     *
     * @return JTextField para ingresar el nombre del usuario.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * Muestra un mensaje emergente al usuario.
     *
     * @param mensaje Texto a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
