package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private CarritoController carritoController;
    private JTextField txtCodigoCarrito;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JButton btnBuscarCarrito;
    private JButton btnModificar;
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;

    private Carrito carritoActual;

    public CarritoModificarView(CarritoController carritoController) {
        this.carritoController = carritoController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Modificar Cantidad Producto en Carrito");
        setSize(700, 400);
        setClosable(true);
        setResizable(true);
        setLayout(new BorderLayout());

        // Panel superior de entrada
        JPanel panelEntrada = new JPanel(new GridLayout(4, 2, 10, 10));
        txtCodigoCarrito = new JTextField();
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();
        btnBuscarCarrito = new JButton("Buscar Carrito");
        btnModificar = new JButton("Modificar");
        btnModificar.setEnabled(false);

        panelEntrada.add(new JLabel("Código Carrito:"));
        panelEntrada.add(txtCodigoCarrito);
        panelEntrada.add(new JLabel("Código Producto:"));
        panelEntrada.add(txtCodigoProducto);
        panelEntrada.add(new JLabel("Nueva Cantidad:"));
        panelEntrada.add(txtCantidad);
        panelEntrada.add(btnBuscarCarrito);
        panelEntrada.add(btnModificar);

        add(panelEntrada, BorderLayout.NORTH);

        // Tabla de productos
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Cantidad", "Precio Unitario", "Subtotal"}, 0);
        tablaCarrito = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos
        btnBuscarCarrito.addActionListener(e -> buscarCarrito());
        btnModificar.addActionListener(e -> modificarCantidad());
    }

    private void buscarCarrito() {
        String codigoStr = txtCodigoCarrito.getText().trim();
        if (!codigoStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Código carrito debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int codigo = Integer.parseInt(codigoStr);
        carritoActual = carritoController.buscarCarrito(codigo);
        if (carritoActual == null) {
            JOptionPane.showMessageDialog(this, "Carrito no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            btnModificar.setEnabled(false);
            modeloTabla.setRowCount(0); // Limpiar tabla
        } else {
            JOptionPane.showMessageDialog(this, "Carrito encontrado con " + carritoActual.obtenerItems().size() + " productos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mostrarDetalleCarrito(carritoActual);
            btnModificar.setEnabled(true);
        }
    }

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

    private void modificarCantidad() {
        if (carritoActual == null) {
            JOptionPane.showMessageDialog(this, "Primero busque un carrito válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigoProdStr = txtCodigoProducto.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        if (!codigoProdStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Código producto debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!cantidadStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser un número entero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int codigoProducto = Integer.parseInt(codigoProdStr);
        int nuevaCantidad = Integer.parseInt(cantidadStr);

        carritoController.modificarCantidad(carritoActual, codigoProducto, nuevaCantidad);
        JOptionPane.showMessageDialog(this, "Cantidad modificada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        mostrarDetalleCarrito(carritoActual); // refrescar tabla

        // Limpiar campos de producto y cantidad, pero dejar el carrito
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }
}
