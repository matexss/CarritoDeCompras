package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;
import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final CarritoController carritoController;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEliminarView productoEliminarView,
                              ProductoModificarView productoModificarView,
                              CarritoAnadirView carritoAnadirView,
                              CarritoDAO carritoDAO,
                              CarritoController carritoController) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoDAO = carritoDAO;
        this.carritoController = carritoController;
        configurarEventos();
    }

    private void configurarEventos() {

        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());

        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());

        productoListaView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                mostrarProductos();
            }
        });

        productoEliminarView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                mostrarProductos();
            }
        });

        productoListaView.getBtnListar().addActionListener(e -> mostrarProductos());

        productoEliminarView.getBtnEliminar().addActionListener(e -> {
            String textoCodigo = productoEliminarView.getTxtNombre().getText();
            if (!textoCodigo.matches("\\d+")) {
                productoEliminarView.mostrarMensaje("Código inválido.");
                return;
            }

            int codigo = Integer.parseInt(textoCodigo);
            Producto p = productoDAO.buscarPorCodigo(codigo);
            if (p == null) {
                productoEliminarView.mostrarMensaje("Producto no encontrado.");
            } else {
                productoDAO.eliminar(codigo);
                productoEliminarView.mostrarMensaje("Producto eliminado.");
                productoEliminarView.getTxtNombre().setText("");
                productoEliminarView.mostrarProductos(productoDAO.listarTodos());
            }
        });

        carritoAnadirView.getBtnAñadir().addActionListener(e -> {
            String codigoTxt = carritoAnadirView.getTxtCodigo().getText();
            String nombre = carritoAnadirView.getTxtNombre().getText();
            String precioTexto = carritoAnadirView.getTxtPrecio().getText();
            String cantidadTexto = carritoAnadirView.getTxtCantidad().getText();

            if (codigoTxt.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
                mostrarMensaje("Todos los campos deben estar llenos.");
                return;
            }

            int codigo = Integer.parseInt(codigoTxt);
            double precio = Double.parseDouble(precioTexto);
            int cantidad = Integer.parseInt(cantidadTexto);

            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                producto = new Producto(codigo, nombre, precio);
            }

            carritoController.agregarProducto(producto, cantidad);

            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTable1().getModel();
            modelo.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    String.format("%.2f", producto.getPrecio()),
                    cantidad,
                    String.format("%.2f", producto.getPrecio() * cantidad)
            });

            calcularTotales(modelo);

            carritoAnadirView.getTxtCodigo().setText("");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
            carritoAnadirView.getTxtCantidad().setText("1");
        });

        carritoAnadirView.getBtnBuscar().addActionListener(e -> {
            String textoCodigo = carritoAnadirView.getTxtCodigo().getText();
            if (!textoCodigo.matches("\\d+")) {
                mostrarMensaje("Código inválido.");
                return;
            }

            int codigo = Integer.parseInt(textoCodigo);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                mostrarMensaje("Producto no encontrado.");
            } else {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        });

        carritoAnadirView.getBtnGuardar().addActionListener(e -> {
            if (carritoController.obtenerItems().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay productos en el carrito.");
                return;
            }

            Carrito carrito = new Carrito();
            carritoController.guardarCarrito(carrito);
            JOptionPane.showMessageDialog(null, "Carrito guardado correctamente.");

            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTable1().getModel();
            modelo.setRowCount(0);
            carritoAnadirView.getTxtSubtotal().setText("");
            carritoAnadirView.getTxtIva().setText("");
            carritoAnadirView.getTxtTotal().setText("");
        });

        productoModificarView.getBuscarButton().addActionListener(e -> {
            String textoCodigo = productoModificarView.getTxtCodigo().getText();
            if (!textoCodigo.matches("\\d+")) {
                productoModificarView.mostrarMensaje("Código inválido.");
                return;
            }

            int codigo = Integer.parseInt(textoCodigo);
            Producto encontrado = productoDAO.buscarPorCodigo(codigo);
            if (encontrado == null) {
                productoModificarView.mostrarMensaje("Producto no encontrado.");
            } else {
                productoModificarView.getLblCodigo().setText(String.valueOf(encontrado.getCodigo()));
                productoModificarView.getLblNombre().setText(encontrado.getNombre());
                productoModificarView.getLblPrecio().setText(String.valueOf(encontrado.getPrecio()));
                productoModificarView.getCbxOpciones().setEnabled(true);
                productoModificarView.getTxtModificar().setEditable(true);
            }
        });

        productoModificarView.getCbxOpciones().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String tipo = (String) productoModificarView.getCbxOpciones().getSelectedItem();
                switch (tipo) {
                    case "Modificar Nombre" -> productoModificarView.getLblMensaje().setText("Nuevo Nombre:");
                    case "Modificar Codigo" -> productoModificarView.getLblMensaje().setText("Nuevo Código:");
                    case "Modificar Precio" -> productoModificarView.getLblMensaje().setText("Nuevo Precio:");
                }
            }
        });

        productoModificarView.getBtnModificar().addActionListener(e -> {
            String codOriginalTxt = productoModificarView.getLblCodigo().getText();
            String tipo = (String) productoModificarView.getCbxOpciones().getSelectedItem();
            String valorMod = productoModificarView.getTxtModificar().getText();

            if (!codOriginalTxt.matches("\\d+")) {
                productoModificarView.mostrarMensaje("Código original inválido.");
                return;
            }

            if (valorMod.isEmpty()) {
                productoModificarView.mostrarMensaje("Debe ingresar un valor para modificar.");
                return;
            }

            int codigoOriginal = Integer.parseInt(codOriginalTxt);
            Producto original = productoDAO.buscarPorCodigo(codigoOriginal);
            if (original == null) {
                productoModificarView.mostrarMensaje("Producto no encontrado.");
                return;
            }

            switch (tipo) {
                case "Modificar Nombre" -> {
                    original.setNombre(valorMod);
                    productoDAO.actualizar(original);
                }
                case "Modificar Codigo" -> {
                    if (!valorMod.matches("\\d+")) {
                        productoModificarView.mostrarMensaje("Nuevo código inválido.");
                        return;
                    }
                    int nuevoCodigo = Integer.parseInt(valorMod);
                    productoDAO.eliminar(original.getCodigo());
                    original.setCodigo(nuevoCodigo);
                    productoDAO.crear(original);
                    productoModificarView.mostrarMensaje("Producto modificado correctamente (código actualizado).");
                    limpiarVistaModificar();
                    return;
                }
                case "Modificar Precio" -> {
                    if (!valorMod.matches("\\d+(\\.\\d+)?")) {
                        productoModificarView.mostrarMensaje("Nuevo precio inválido.");
                        return;
                    }
                    original.setPrecio(Double.parseDouble(valorMod));
                    productoDAO.actualizar(original);
                }
            }

            productoModificarView.mostrarMensaje("Producto modificado correctamente.");
            limpiarVistaModificar();
        });
    }

    private void guardarProducto() {
        String codTxt = productoAnadirView.getTxtCodigo().getText();
        String nombre = productoAnadirView.getTxtNombre().getText();
        String precioTxt = productoAnadirView.getTxtPrecio().getText();

        if (!codTxt.matches("\\d+") || !precioTxt.matches("\\d+(\\.\\d+)?") || nombre.isEmpty()) {
            productoAnadirView.mostrarMensaje("Datos inválidos.");
            return;
        }

        int codigo = Integer.parseInt(codTxt);
        double precio = Double.parseDouble(precioTxt);

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado.");
        productoAnadirView.limpiarCampos();
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.getTxtBuscar().setText("");
        productoListaView.mostrarProductos(productos);
    }

    private void mostrarProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
        productoEliminarView.mostrarProductos(productoDAO.listarTodos());
    }

    private void calcularTotales(DefaultTableModel modelo) {
        double subtotal = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            try {
                int cantidad = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                String precioStr = modelo.getValueAt(i, 2).toString().replace(".", "").replace(",", ".").replace("$", "").trim();
                double precio = Double.parseDouble(precioStr);
                subtotal += cantidad * precio;
            } catch (Exception e) {
                System.err.println("[ERROR] Al calcular totales: " + e.getMessage());
            }
        }

        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", subtotal));
        carritoAnadirView.getTxtIva().setText(String.format("%.2f", iva));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", total));
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private void limpiarVistaModificar() {
        productoModificarView.getTxtModificar().setText("");
        productoModificarView.getCbxOpciones().setEnabled(false);
        productoModificarView.getLblCodigo().setText("");
        productoModificarView.getLblNombre().setText("");
        productoModificarView.getLblPrecio().setText("");
        productoModificarView.getTxtCodigo().setText("");
    }
}
