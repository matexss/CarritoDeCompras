package ec.edu.ups.controlador;
import javax.swing.JOptionPane;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAnadirView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private Carrito carrito;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carrito = new Carrito();
        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAñadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });
    }

    private void guardarCarrito() {
        carritoDAO.guardar(carrito); // asegúrate de tener este método en CarritoDAO
        JOptionPane.showMessageDialog(carritoAnadirView, "Carrito creado correctamente");
        System.out.println(carritoDAO.listarProductos());
    }

    private void anadirProducto() {
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            int cantidad = Integer.parseInt(carritoAnadirView.getComboBox1().getSelectedItem().toString());

            carrito.agregarProducto(producto, cantidad);
            cargarProductos();
            mostrarTotales();

            // Mostrar datos en los campos también
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(carritoAnadirView, "Error al añadir producto: " + ex.getMessage());
        }
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTable1().getModel();
        modelo.setRowCount(0); // Limpia tabla

        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad()
            });
        }
    }

    private void mostrarTotales() {
        carritoAnadirView.getTxtSubtotal().setText(String.valueOf(carrito.calcularSubtotal()));
        carritoAnadirView.getTxtIva().setText(String.valueOf(carrito.calcularIVA()));
        carritoAnadirView.getTxtTotal().setText(String.valueOf(carrito.calcularTotal()));
    }
}
