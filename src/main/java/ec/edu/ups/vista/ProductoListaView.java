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
 * Vista para listar y buscar productos registrados en el sistema.
 * Muestra una tabla con los productos existentes, permite búsquedas por nombre
 * y actualiza los textos con soporte de internacionalización.
 *
 * @author Mateo
 * @version 1.0
 */
public class ProductoListaView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JLabel lblBuscar;
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    /**
     * Constructor de la vista.
     *
     * @param mensajes Manejador de internacionalización.
     */
    public ProductoListaView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;
        this.locale = mensajes.getLocale();
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa y configura los componentes gráficos de la vista.
     */
    private void initComponents() {
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(60, 60);

        Color fondo = new Color(255, 228, 232);
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelSuperior = new JPanel(null);
        panelSuperior.setPreferredSize(new Dimension(500, 90));
        panelSuperior.setBackground(fondo);

        JLabel titulo = new JLabel("Lista de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBounds(150, 5, 300, 30);
        panelSuperior.add(titulo);

        lblBuscar = new JLabel();
        lblBuscar.setBounds(20, 45, 100, 25);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(120, 45, 150, 25);

        btnBuscar = new JButton(IconUtil.cargarIcono("search.png"));
        btnBuscar.setBounds(290, 45, 80, 25);

        btnListar = new JButton(IconUtil.cargarIcono("user-list.png"));
        btnListar.setBounds(380, 45, 80, 25);

        panelSuperior.add(lblBuscar);
        panelSuperior.add(txtBuscar);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(btnListar);

        modelo = new DefaultTableModel(new Object[]{"#", "#", "#"}, 0);
        tblProductos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblProductos);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    /**
     * Actualiza los textos de los componentes según el idioma configurado.
     *
     * @param mensajes Manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        locale = mensajes.getLocale();
        setTitle(mensajes.get("producto.titulo.listar"));
        lblBuscar.setText(mensajes.get("global.nombre") + ":");
        btnBuscar.setText(mensajes.get("global.boton.buscar"));
        btnListar.setText(mensajes.get("producto.boton.listar"));

        modelo.setColumnIdentifiers(new String[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio")
        });
    }

    /**
     * Obtiene el campo de texto donde se ingresa el nombre a buscar.
     *
     * @return JTextField de búsqueda.
     */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    /**
     * Obtiene el botón para ejecutar la búsqueda.
     *
     * @return JButton buscar.
     */
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    /**
     * Obtiene el botón para listar todos los productos.
     *
     * @return JButton listar.
     */
    public JButton getBtnListar() {
        return btnListar;
    }

    /**
     * Devuelve la tabla que contiene la lista de productos.
     *
     * @return JTable con los productos.
     */
    public JTable getTblProductos() {
        return tblProductos;
    }

    /**
     * Muestra en la tabla los productos pasados como parámetro.
     *
     * @param productos Lista de productos a mostrar.
     */
    public void mostrarProductos(List<Producto> productos) {
        modelo.setRowCount(0);
        for (Producto producto : productos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getPrecio()
            };
            modelo.addRow(fila);
        }
    }
}
