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
    private JPanel panelPrincipal;
    private JPanel panel1;
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

        String[] columnas = {"Código", "Nombre", "Precio", "Cantidad", "Subtotal"};
        modelo = new DefaultTableModel(columnas, 0);
        table = new JTable(modelo);
        add(new JScrollPane(table), BorderLayout.CENTER);

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
                if (codigoTexto.isBlank()) {
                    mostrarMensaje("Por favor ingrese el código del producto.");
                    return;
                }
                if (!codigoTexto.matches("\\d+")) {
                    mostrarMensaje("Código inválido. Use solo números.");
                    return;
                }
                int codigo = Integer.parseInt(codigoTexto);
                Producto producto = carritoController.buscarProductoPorCodigo(codigo);

                if (producto != null) {
                    txtNombre.setText(producto.getNombre());
                    txtPrecio.setText(String.valueOf(producto.getPrecio()));
                } else {
                    mostrarMensaje("Producto no encontrado.");
                    txtNombre.setText("");
                    txtPrecio.setText("");
                }
            }
        });

        btnAñadir.addActionListener(e -> {
            String codigoTexto = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String precioTexto = txtPrecio.getText().trim();
            String cantidadTexto = txtCantidad.getText().trim();

            // Validaciones
            if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
                mostrarMensaje("Todos los campos deben estar llenos.");
                return;
            }

            int codigo;
            try {
                codigo = Integer.parseInt(codigoTexto);
            } catch (NumberFormatException ex) {
                mostrarMensaje("Código inválido.");
                return;
            }

            double precio = Double.parseDouble(precioTexto);
            int cantidad = Integer.parseInt(cantidadTexto);

            Producto producto = new Producto();
            producto.setCodigo(codigo); // ✅ ASIGNAR CÓDIGO CORRECTAMENTE
            producto.setNombre(nombre);
            producto.setPrecio(precio);

            carritoController.agregarProducto(producto, cantidad);

            double subtotal = cantidad * precio;
            modelo.addRow(new Object[]{
                    codigo,
                    nombre,
                    FormateadorUtils.formatearMoneda(precio, locale),
                    cantidad,
                    FormateadorUtils.formatearMoneda(subtotal, locale)
            });

            actualizarTotales();

            // Limpiar
            txtCodigo.setText("");
            txtNombre.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("1");
        });


        btnGuardar.addActionListener(e -> {
            System.out.println(">>> [DEBUG] Botón Guardar presionado");

            if (carritoController.obtenerItems().isEmpty()) {
                mostrarMensaje("No hay productos en el carrito.");
                return;
            }

            Carrito carrito = new Carrito();
            System.out.println(">>> [DEBUG] Carrito creado, agregando productos");

            for (ItemCarrito item : carritoController.obtenerItems()) {
                carrito.agregarProducto(item.getProducto(), item.getCantidad());
            }

            carritoController.guardarCarrito(carrito);
            mostrarMensaje("Carrito guardado correctamente.");

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

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Getters
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
}
