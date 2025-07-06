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

        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Cantidad", "Precio Unitario", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo permitir editar la cantidad
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
        JButton btnGuardarCambios = new JButton("Guardar Cambios");
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnGuardarCambios);
        add(panelInferior, BorderLayout.SOUTH);
        btnGuardarCambios.addActionListener(e -> guardarCambios());
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
            modeloTabla.setRowCount(0);
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
    private void guardarCambios() {
        if (carritoActual == null) {
            JOptionPane.showMessageDialog(this, "No hay carrito cargado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int codigoProducto = (int) modeloTabla.getValueAt(i, 0);
            int nuevaCantidad = (int) modeloTabla.getValueAt(i, 2);
            carritoController.modificarCantidad(carritoActual, codigoProducto, nuevaCantidad);
        }

        JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.");
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
        mostrarDetalleCarrito(carritoActual);
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }
    public void buscarCarritoDesdeExterno(int codigo) {
        txtCodigoCarrito.setText(String.valueOf(codigo));
        buscarCarrito();
    }

    public void setCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        txtCodigoCarrito.setText(String.valueOf(carrito.getCodigo()));
        txtCodigoCarrito.setEditable(false);
        btnBuscarCarrito.setEnabled(false);  // ya no se necesita buscarlo
        mostrarDetalleCarrito(carrito);
        btnModificar.setEnabled(true);
    }
}
