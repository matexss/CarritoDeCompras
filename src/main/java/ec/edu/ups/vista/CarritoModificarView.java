package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.ActualizableConIdioma;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

/**
 * Vista que permite modificar los productos de un carrito existente.
 * El usuario puede actualizar las cantidades de los productos, ya sea individualmente o en lote.
 * Soporta internacionalización dinámica.
 *
 * @author Mateo
 * @version 1.0
 */
public class CarritoModificarView extends JInternalFrame implements ActualizableConIdioma {

    private JPanel panelPrincipal;
    private CarritoController carritoController;
    private JTextField txtCodigoCarrito;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JButton btnBuscarCarrito;
    private JButton btnModificar;
    private JButton btnGuardarCambios;
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;
    private Carrito carritoActual;
    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    private JLabel lblCarrito;
    private JLabel lblProducto;
    private JLabel lblCantidad;

    /**
     * Constructor principal.
     *
     * @param carritoController controlador de lógica del carrito.
     * @param mensajes manejador de internacionalización.
     */
    public CarritoModificarView(CarritoController carritoController, MensajeInternacionalizacionHandler mensajes) {
        this.carritoController = carritoController;
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        initComponents();
        actualizarTextos(mensajes);
    }

    /**
     * Inicializa todos los componentes gráficos de la vista.
     */
    private void initComponents() {
        Color fondo = new Color(255, 228, 232);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        JLabel titulo = new JLabel("Modificar Carrito", SwingConstants.CENTER);
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(new Color(80, 20, 60));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().setBackground(fondo);
        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);

        setSize(700, 400);
        setClosable(true);
        setResizable(true);

        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 10, 10));
        panelEntrada.setBackground(fondo);

        lblCarrito = new JLabel();
        lblProducto = new JLabel();
        lblCantidad = new JLabel();

        txtCodigoCarrito = new JTextField();
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();

        btnBuscarCarrito = new JButton();
        btnModificar = new JButton();
        btnModificar.setEnabled(false);

        panelEntrada.add(lblCarrito);
        panelEntrada.add(txtCodigoCarrito);
        panelEntrada.add(lblProducto);
        panelEntrada.add(txtCodigoProducto);
        panelEntrada.add(lblCantidad);
        panelEntrada.add(txtCantidad);
        panelEntrada.add(btnBuscarCarrito);
        panelEntrada.add(btnModificar);

        add(panelEntrada, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"", "", "", "", ""}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 2) return Integer.class;
                if (columnIndex == 3 || columnIndex == 4) return Double.class;
                return String.class;
            }
        };
        tablaCarrito = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        add(scrollPane, BorderLayout.CENTER);

        btnGuardarCambios = new JButton();
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(fondo);
        panelInferior.add(btnGuardarCambios);
        add(panelInferior, BorderLayout.SOUTH);

        btnGuardarCambios.addActionListener(e -> guardarCambios());
        btnBuscarCarrito.addActionListener(e -> buscarCarrito());
        btnModificar.addActionListener(e -> modificarCantidad());
    }

    /**
     * Actualiza los textos visibles de la vista, según el idioma actual.
     *
     * @param mensajes manejador de internacionalización.
     */
    @Override
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));
        setTitle(mensajes.get("carrito.modificar.titulo.app"));

        lblCarrito.setText(mensajes.get("global.codigo") + " " + mensajes.get("global.carrito") + ":");
        lblProducto.setText(mensajes.get("global.codigo") + " " + mensajes.get("global.producto") + ":");
        lblCantidad.setText(mensajes.get("global.nuevaCantidad") + ":");

        btnBuscarCarrito.setText(mensajes.get("global.boton.buscar"));
        btnModificar.setText(mensajes.get("global.boton.modificar"));
        btnGuardarCambios.setText(mensajes.get("global.boton.guardar"));

        modeloTabla.setColumnIdentifiers(new Object[]{
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.precio"),
                mensajes.get("global.subtotal")
        });
    }

    /**
     * Busca un carrito por código e inicializa su visualización.
     */
    private void buscarCarrito() {
        String codigoStr = txtCodigoCarrito.getText().trim();
        if (!codigoStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, mensajes.get("carrito.buscar.codigo.error"), mensajes.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(codigoStr);
        carritoActual = carritoController.buscarCarrito(codigo);
        if (carritoActual == null) {
            JOptionPane.showMessageDialog(this, mensajes.get("carrito.buscar.noencontrado"), mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
            btnModificar.setEnabled(false);
            modeloTabla.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(this, mensajes.get("carrito.buscar.exito") + carritoActual.obtenerItems().size(), mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
            mostrarDetalleCarrito(carritoActual);
            btnModificar.setEnabled(true);
        }
    }

    /**
     * Muestra en la tabla los ítems del carrito actual.
     *
     * @param carrito el carrito cuyos ítems se van a mostrar.
     */
    private void mostrarDetalleCarrito(Carrito carrito) {
        modeloTabla.setRowCount(0);
        List<ItemCarrito> items = carrito.obtenerItems();
        for (ItemCarrito item : items) {
            Producto p = item.getProducto();
            int cantidad = item.getCantidad();
            double precio = p.getPrecio();
            modeloTabla.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    cantidad,
                    precio,
                    cantidad * precio
            });
        }
    }

    /**
     * Guarda todos los cambios de cantidades editadas directamente en la tabla.
     */
    private void guardarCambios() {
        if (carritoActual == null) {
            JOptionPane.showMessageDialog(this, mensajes.get("carrito.guardar.nohay"), mensajes.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int codigoProducto = (int) modeloTabla.getValueAt(i, 0);
            int nuevaCantidad = (int) modeloTabla.getValueAt(i, 2);
            carritoController.modificarCantidad(carritoActual, codigoProducto, nuevaCantidad);
        }

        JOptionPane.showMessageDialog(this, mensajes.get("carrito.modificar.exito"), mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Modifica una única cantidad ingresada manualmente desde los campos de texto.
     */
    private void modificarCantidad() {
        if (carritoActual == null) {
            JOptionPane.showMessageDialog(this, mensajes.get("carrito.buscar.primero"), mensajes.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigoProdStr = txtCodigoProducto.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        if (!codigoProdStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, mensajes.get("producto.codigo.entero"), mensajes.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!cantidadStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, mensajes.get("producto.cantidad.entero"), mensajes.get("global.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigoProducto = Integer.parseInt(codigoProdStr);
        int nuevaCantidad = Integer.parseInt(cantidadStr);

        carritoController.modificarCantidad(carritoActual, codigoProducto, nuevaCantidad);
        JOptionPane.showMessageDialog(this, mensajes.get("carrito.modificar.uno.exito"), mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
        mostrarDetalleCarrito(carritoActual);
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }

    /**
     * Método externo que permite cargar un carrito desde otra vista.
     *
     * @param codigo código del carrito a cargar.
     */
    public void buscarCarritoDesdeExterno(int codigo) {
        txtCodigoCarrito.setText(String.valueOf(codigo));
        buscarCarrito();
    }

    /**
     * Carga un carrito ya existente en la vista.
     *
     * @param carrito carrito a visualizar y modificar.
     */
    public void setCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        txtCodigoCarrito.setText(String.valueOf(carrito.getCodigo()));
        txtCodigoCarrito.setEditable(false);
        btnBuscarCarrito.setEnabled(false);
        mostrarDetalleCarrito(carrito);
        btnModificar.setEnabled(true);
    }
}
