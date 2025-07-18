package ec.edu.ups.vista;

import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Vista de la aplicación que permite añadir un nuevo producto.
 * Implementa soporte de internacionalización y diseño personalizado.
 * Esta clase pertenece al módulo de productos del sistema.
 *
 * @author Mateo
 * @version 1.0
 */
public class ProductoAnadirView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAceptar;
    private JButton btnLimpiar;

    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    /**
     * Constructor que inicializa la vista de añadir producto con soporte de idioma.
     *
     * @param mensajes Objeto manejador de internacionalización.
     */
    public ProductoAnadirView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Método que inicializa todos los componentes de la interfaz gráfica.
     */
    private void initComponents() {
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(30, 30);

        Color fondo = new Color(255, 228, 232);
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(fondo);

        JLabel titulo = new JLabel("Añadir Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBounds(120, 5, 200, 30);
        panelPrincipal.add(titulo);

        lblCodigo = new JLabel();
        lblCodigo.setBounds(20, 40, 100, 25);
        txtCodigo = new JTextField();
        txtCodigo.setBounds(120, 40, 200, 25);

        lblNombre = new JLabel();
        lblNombre.setBounds(20, 75, 100, 25);
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 75, 200, 25);

        lblPrecio = new JLabel();
        lblPrecio.setBounds(20, 110, 100, 25);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 110, 200, 25);

        btnAceptar = new JButton(IconUtil.cargarIcono("logout.png"));
        btnAceptar.setBounds(70, 160, 100, 30);

        btnLimpiar = new JButton(IconUtil.cargarIcono("eliminar.png"));
        btnLimpiar.setBounds(210, 160, 100, 30);

        panelPrincipal.add(lblCodigo);
        panelPrincipal.add(txtCodigo);
        panelPrincipal.add(lblNombre);
        panelPrincipal.add(txtNombre);
        panelPrincipal.add(lblPrecio);
        panelPrincipal.add(txtPrecio);
        panelPrincipal.add(btnAceptar);
        panelPrincipal.add(btnLimpiar);

        setContentPane(panelPrincipal);
    }

    /**
     * Método que actualiza todos los textos de la vista según el idioma actual.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.anadir"));
        lblCodigo.setText(mensajes.get("global.codigo") + ":");
        lblNombre.setText(mensajes.get("global.nombre") + ":");
        lblPrecio.setText(mensajes.get("global.precio") + ":");
        btnAceptar.setText(mensajes.get("global.boton.aceptar"));
        btnLimpiar.setText(mensajes.get("global.boton.limpiar"));
    }

    /**
     * Devuelve el campo de texto para el código del producto.
     *
     * @return JTextField del código.
     */
    public JTextField getTxtCodigo() { return txtCodigo; }

    /**
     * Devuelve el campo de texto para el nombre del producto.
     *
     * @return JTextField del nombre.
     */
    public JTextField getTxtNombre() { return txtNombre; }

    /**
     * Devuelve el campo de texto para el precio del producto.
     *
     * @return JTextField del precio.
     */
    public JTextField getTxtPrecio() { return txtPrecio; }

    /**
     * Devuelve el botón de aceptar.
     *
     * @return JButton de aceptar.
     */
    public JButton getBtnAceptar() { return btnAceptar; }

    /**
     * Devuelve el botón de limpiar.
     *
     * @return JButton de limpiar.
     */
    public JButton getBtnLimpiar() { return btnLimpiar; }

    /**
     * Muestra un mensaje al usuario en una ventana emergente.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia todos los campos de entrada del formulario.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}
