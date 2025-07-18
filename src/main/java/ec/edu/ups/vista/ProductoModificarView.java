package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Vista gráfica para modificar los atributos de un producto existente.
 * Permite buscar por código y modificar nombre, precio o código del producto.
 * Implementa soporte de internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class ProductoModificarView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton buscarButton;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JComboBox<String> cbxOpciones;
    private JTextField txtModificar;
    private JLabel lblMensaje;
    private JButton btnModificar;

    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    /**
     * Constructor de la vista de modificación de productos.
     *
     * @param mensajes Manejador de internacionalización para aplicar traducciones dinámicas.
     */
    public ProductoModificarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa y organiza todos los componentes gráficos de la interfaz.
     */
    private void initComponents() {
        setSize(800, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(100, 100);

        Color fondo = new Color(255, 228, 232);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(fondo);

        JLabel titulo = new JLabel("Modificar Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(80, 20, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 3;
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(12);
        panel.add(txtCodigo, gbc);

        gbc.gridx = 2;
        buscarButton = new JButton("Buscar", IconUtil.cargarIcono("search.png", 18, 18));
        panel.add(buscarButton, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Nombre actual:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        lblNombre = new JLabel("---");
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD));
        panel.add(lblNombre, gbc);

        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Precio actual:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        lblPrecio = new JLabel("---");
        lblPrecio.setFont(lblPrecio.getFont().deriveFont(Font.BOLD));
        panel.add(lblPrecio, gbc);

        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy++;
        lblMensaje = new JLabel("Seleccione qué desea modificar:");
        panel.add(lblMensaje, gbc);
        gbc.gridx = 1;
        cbxOpciones = new JComboBox<>();
        cbxOpciones.setEnabled(false);
        panel.add(cbxOpciones, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Nuevo valor:"), gbc);
        gbc.gridx = 1;
        txtModificar = new JTextField(15);
        txtModificar.setEditable(false);
        panel.add(txtModificar, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        btnModificar = new JButton("Modificar", IconUtil.cargarIcono("editar.png", 18, 18));
        panel.add(btnModificar, gbc);

        setContentPane(panel);

        // Elemento oculto (necesario para compatibilidad con lógica previa)
        lblCodigo = new JLabel();
        lblCodigo.setVisible(false);
        panel.add(lblCodigo);
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma seleccionado.
     *
     * @param mensajes Manejador de mensajes internacionalizados.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        this.locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.modificar"));
        buscarButton.setText(mensajes.get("global.boton.buscar"));
        lblMensaje.setText(mensajes.get("producto.msg.datoAModificar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));

        cbxOpciones.removeAllItems();
        cbxOpciones.addItem("Modificar Nombre");
        cbxOpciones.addItem("Modificar Codigo");
        cbxOpciones.addItem("Modificar Precio");
    }

    // ==== Getters para acceso desde el controlador ====

    /**
     * Campo de texto para ingresar el código del producto a modificar.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    /**
     * Botón para ejecutar la búsqueda del producto.
     */
    public JButton getBuscarButton() {
        return buscarButton;
    }

    /**
     * Label oculto utilizado para compatibilidad con el controlador.
     */
    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    /**
     * Campo de texto donde se ingresa el nuevo valor a modificar.
     */
    public JTextField getTxtModificar() {
        return txtModificar;
    }

    /**
     * Label que muestra el nombre actual del producto.
     */
    public JLabel getLblNombre() {
        return lblNombre;
    }

    /**
     * Label que muestra el precio actual del producto.
     */
    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    /**
     * ComboBox que permite elegir qué campo del producto se desea modificar.
     */
    public JComboBox<String> getCbxOpciones() {
        return cbxOpciones;
    }

    /**
     * Label que muestra el mensaje de selección de campo a modificar.
     */
    public JLabel getLblMensaje() {
        return lblMensaje;
    }

    /**
     * Botón que ejecuta la modificación del producto.
     */
    public JButton getBtnModificar() {
        return btnModificar;
    }

    /**
     * Muestra un mensaje en pantalla al usuario.
     *
     * @param mensaje Texto del mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
