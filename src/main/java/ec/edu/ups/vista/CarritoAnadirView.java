package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

public class CarritoAnadirView extends JInternalFrame {
   //NO TOCAR NI BORRAR
    private JPanel panelPrincipal;
    private JPanel panel1;
    // NO TOCAR NI BORRAR
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnGuardar;
    private JTable table;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;
    private DefaultTableModel modelo;

    private CarritoController carritoController;
    private Locale locale;

    public CarritoAnadirView(CarritoController carritoController) {
        super("Añadir al Carrito", true, true, true, true);
        this.carritoController = carritoController;
        this.locale = new Locale("es", "EC");

        initComponents();
        initListeners();
    }

    private void initComponents() {
        setSize(750, 550);
        setLayout(new BorderLayout());

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(2, 5, 10, 10));
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtNombre.setEditable(false);
        txtPrecio = new JTextField();
        txtPrecio.setEditable(false);
        txtCantidad = new JTextField("1");
        btnBuscar = new JButton("Buscar");
        btnAñadir = new JButton("Añadir");

        panelBusqueda.add(new JLabel("Código:"));
        panelBusqueda.add(txtCodigo);
        panelBusqueda.add(new JLabel("Nombre:"));
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);

        panelBusqueda.add(new JLabel("Precio:"));
        panelBusqueda.add(txtPrecio);
        panelBusqueda.add(new JLabel("Cantidad:"));
        panelBusqueda.add(txtCantidad);
        panelBusqueda.add(btnAñadir);

        add(panelBusqueda, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo = new DefaultTableModel(columnas, 0);
        table = new JTable(modelo);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel inferior: totales + botón guardar
        JPanel panelInferior = new JPanel(new BorderLayout());

        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 10, 5));
        txtSubtotal = new JTextField();
        txtSubtotal.setEditable(false);
        txtIva = new JTextField();
        txtIva.setEditable(false);
        txtTotal = new JTextField();
        txtTotal.setEditable(false);

        panelTotales.add(new JLabel("Subtotal:"));
        panelTotales.add(txtSubtotal);
        panelTotales.add(new JLabel("IVA 12%:"));
        panelTotales.add(txtIva);
        panelTotales.add(new JLabel("Total:"));
        panelTotales.add(txtTotal);

        panelInferior.add(panelTotales, BorderLayout.CENTER);

        btnGuardar = new JButton("Guardar Carrito");
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);
        panelInferior.add(panelBoton, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void initListeners() {
        btnBuscar.addActionListener(e -> {
            String codigoTexto = txtCodigo.getText().trim();
            if (!codigoTexto.isEmpty()) {
                int codigo = Integer.parseInt(codigoTexto);
                Producto producto = carritoController.buscarProductoPorCodigo(codigo);
                if (producto != null) {
                    txtNombre.setText(producto.getNombre());
                    txtPrecio.setText(String.valueOf(producto.getPrecio()));
                } else {
                    mostrarMensaje("Producto no encontrado.");
                }
            }
        });



        btnAñadir.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String precioTexto = txtPrecio.getText().trim();
            String cantidadTexto = txtCantidad.getText().trim();

            // Validar campos vacíos
            if (nombre.isEmpty() || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
                mostrarMensaje("Todos los campos deben estar llenos.");
                return;
            }

            // Validar que precio y cantidad sean numéricos válidos
            if (!precioTexto.matches("\\d+(\\.\\d+)?")) {
                mostrarMensaje("Precio inválido. Usa solo números, por ejemplo: 10.50");
                return;
            }

            if (!cantidadTexto.matches("\\d+")) {
                mostrarMensaje("Cantidad inválida. Usa solo números enteros.");
                return;
            }

            double precio = Double.parseDouble(precioTexto);
            int cantidad = Integer.parseInt(cantidadTexto);

            // Crear el producto
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);

            // Agregar al carrito
            carritoController.agregarProducto(producto, cantidad);

            double subtotal = cantidad * precio;
            modelo.addRow(new Object[]{
                    nombre,
                    FormateadorUtils.formatearMoneda(precio, locale),
                    cantidad,
                    FormateadorUtils.formatearMoneda(subtotal, locale)
            });

            actualizarTotales();

            // Limpiar campos
            txtCodigo.setText("");
            txtNombre.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("1");
        });

        btnGuardar.addActionListener(e -> {
            int filas = table.getRowCount();

            if (filas == 0) {
                mostrarMensaje("No hay productos en el carrito.");
                return;
            }

            Carrito carrito = new Carrito();

            for (int i = 0; i < filas; i++) {
                String nombre = table.getValueAt(i, 0).toString().trim();
                String precioTexto = table.getValueAt(i, 1).toString().replace("$", "").replace(",", "").trim();
                String cantidadTexto = table.getValueAt(i, 2).toString().trim();

                // Validar campos
                if (nombre.isEmpty() || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
                    mostrarMensaje("Hay un producto con datos incompletos en la tabla.");
                    return;
                }

                if (!precioTexto.matches("\\d+(\\.\\d+)?")) {
                    mostrarMensaje("Precio inválido en la tabla: " + precioTexto);
                    return;
                }

                if (!cantidadTexto.matches("\\d+")) {
                    mostrarMensaje("Cantidad inválida en la tabla: " + cantidadTexto);
                    return;
                }

                double precio = Double.parseDouble(precioTexto);
                int cantidad = Integer.parseInt(cantidadTexto);

                Producto producto = new Producto();
                producto.setNombre(nombre);
                producto.setPrecio(precio);

                carrito.agregarProducto(producto, cantidad);
            }

            // Guardar carrito
            carritoController.guardarCarrito(carrito);

            mostrarMensaje("Carrito guardado correctamente.");

            // Limpiar tabla y totales
            modelo.setRowCount(0);
            txtSubtotal.setText("");
            txtIva.setText("");
            txtTotal.setText("");
        });

    }

    private void actualizarTotales() {
        double subtotal = carritoController.obtenerItems().stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        txtSubtotal.setText(FormateadorUtils.formatearMoneda(subtotal, locale));
        txtIva.setText(FormateadorUtils.formatearMoneda(iva, locale));
        txtTotal.setText(FormateadorUtils.formatearMoneda(total, locale));
    }

    // Getters para controlador
    public JButton getBtnAñadir() {
        return btnAñadir;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JTable getTable1() {
        return table;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
