package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.IconUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista para eliminar productos del sistema.
 * Presenta una interfaz con búsqueda por código y una tabla para visualizar los productos.
 * Incluye soporte de internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class ProductoEliminarView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton btnEliminar;
    private JTable tblProductos;
    private JLabel lblNombre;
    private JLabel lblCodigo;
    private DefaultTableModel modelo;

    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    /**
     * Constructor de la vista para eliminar productos.
     *
     * @param mensajes Manejador de internacionalización.
     */
    public ProductoEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa los componentes gráficos de la ventana.
     */
    private void initComponents() {
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(50, 50);

        Color fondo = new Color(255, 228, 232);
        panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel(null);
        panelSuperior.setPreferredSize(new Dimension(600, 90));
        panelSuperior.setBackground(fondo);

        JLabel titulo = new JLabel("Eliminar Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBounds(200, 5, 300, 30);
        panelSuperior.add(titulo);

        lblNombre = new JLabel();
        lblNombre.setBounds(20, 45, 100, 25);

        txtNombre = new JTextField();
        txtNombre.setBounds(130, 45, 200, 25);

        btnEliminar = new JButton(IconUtil.cargarIcono("eliminar.png"));
        btnEliminar.setBounds(350, 45, 120, 25);

        panelSuperior.add(lblNombre);
        panelSuperior.add(txtNombre);
        panelSuperior.add(btnEliminar);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Código", "Nombre", "Precio"});
        tblProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblProductos);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma actual.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        this.locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.eliminar"));
        lblNombre.setText(mensajes.get("global.codigo") + ":");
        btnEliminar.setText(mensajes.get("global.boton.eliminar"));

        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio")
        });
    }

    /**
     * Devuelve el campo de texto del nombre del producto a buscar.
     *
     * @return JTextField del nombre o código.
     */
    public JTextField getTxtNombre() {
        return txtNombre;
    }

    /**
     * Devuelve el botón para eliminar productos.
     *
     * @return JButton de eliminar.
     */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    /**
     * Devuelve la tabla que muestra los productos listados.
     *
     * @return JTable de productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Muestra un mensaje al usuario mediante una ventana emergente.
     *
     * @param mensaje El texto del mensaje.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Muestra una lista de productos en la tabla de la interfaz.
     *
     * @param productos Lista de productos a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        modelo.setRowCount(0);
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    String.format("%.2f", p.getPrecio())
            });
        }
    }
}
