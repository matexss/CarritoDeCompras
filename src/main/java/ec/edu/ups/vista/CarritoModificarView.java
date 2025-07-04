package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import java.awt.*;

public class CarritoModificarView extends JInternalFrame {

    private CarritoController carritoController;
    private JPanel panelPrincipal;
    private JTextField txtCodigoCarrito;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JButton btnBuscarCarrito;
    private JButton btnModificar;

    private Carrito carritoActual;

    public CarritoModificarView(CarritoController carritoController) {
        this.carritoController = carritoController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Modificar Cantidad Producto en Carrito");
        setSize(400, 250);
        setClosable(true);
        setResizable(true);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblCodigoCarrito = new JLabel("Código Carrito:");
        txtCodigoCarrito = new JTextField();

        JLabel lblCodigoProducto = new JLabel("Código Producto:");
        txtCodigoProducto = new JTextField();

        JLabel lblCantidad = new JLabel("Nueva Cantidad:");
        txtCantidad = new JTextField();

        btnBuscarCarrito = new JButton("Buscar Carrito");
        btnModificar = new JButton("Modificar");
        btnModificar.setEnabled(false);

        add(lblCodigoCarrito);
        add(txtCodigoCarrito);
        add(lblCodigoProducto);
        add(txtCodigoProducto);
        add(lblCantidad);
        add(txtCantidad);
        add(btnBuscarCarrito);
        add(btnModificar);

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
        } else {
            JOptionPane.showMessageDialog(this, "Carrito encontrado con " + carritoActual.obtenerItems().size() + " productos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            btnModificar.setEnabled(true);
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

        btnModificar.setEnabled(false);
        carritoActual = null;
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCodigoCarrito.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
    }



}
