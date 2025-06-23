package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoEliminarView;
import ec.edu.ups.vista.ProductoListaView;
import ec.edu.ups.vista.ProductoModificarView;
import ec.edu.ups.vista.CarritoAnadirView;

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

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEliminarView productoEliminarView,
                              ProductoModificarView productoModificarView,
                              CarritoAnadirView carritoAnadirView,
                              CarritoDAO carritoDAO) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoDAO = carritoDAO;

        configurarEventos();
    }

    private void configurarEventos() {

        // Evento Guardar Producto
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());

        // Buscar producto por nombre en lista
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());

        // Actualizar lista al activar ventana interna ProductoListaView
        productoListaView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                mostrarProductos();
            }
        });

        // Actualizar lista al activar ventana interna ProductoEliminarView
        productoEliminarView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                mostrarProductos();
            }
        });

        // Botón listar productos (en ProductoListaView)
        productoListaView.getBtnListar().addActionListener(e -> mostrarProductos());

        // Botón eliminar producto
        productoEliminarView.getBtnEliminar().addActionListener(e -> {
            int codigo = Integer.parseInt(productoEliminarView.getTxtNombre().getText());
            Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
            if (productoEncontrado == null) {
                productoEliminarView.mostrarMensaje("El producto no existe");
            } else {
                productoDAO.eliminar(codigo);
                productoEliminarView.mostrarMensaje("El producto " + productoEncontrado.getNombre() + " fue eliminado correctamente");
                productoEliminarView.getTxtNombre().setText("");
                productoEliminarView.mostrarProductos(productoDAO.listarTodos());
            }
        });

        // Añadir producto al carrito
        carritoAnadirView.getBtnAñadir().addActionListener(e -> {
            String nombre = carritoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(carritoAnadirView.getTxtPrecio().getText());
            int cantidad = Integer.parseInt(carritoAnadirView.getComboBox1().getSelectedItem().toString());

            double totalParcial = precio * cantidad;

            DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTable1().getModel();
            modelo.addRow(new Object[]{nombre, precio, cantidad, totalParcial});

            calcularTotales(modelo);
        });

        // Buscar producto por código en CarritoAnadirView
        carritoAnadirView.getBtnBuscar().addActionListener(e -> {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                JOptionPane.showMessageDialog(null, "El producto no existe");
            } else {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        });

        // Buscar producto en modificar
        productoModificarView.getBuscarButton().addActionListener(e -> {
            int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
            Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
            if (productoEncontrado == null) {
                productoModificarView.mostrarMensaje("El producto no existe");
            } else {
                productoModificarView.getLblCodigo().setText(String.valueOf(productoEncontrado.getCodigo()));
                productoModificarView.getLblNombre().setText(productoEncontrado.getNombre());
                productoModificarView.getLblPrecio().setText(String.valueOf(productoEncontrado.getPrecio()));
                productoModificarView.getCbxOpciones().setEnabled(true);
                productoModificarView.getTxtModificar().setEditable(true);
            }
        });

        // Cambio en combo modificar opción
        productoModificarView.getCbxOpciones().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String tipo = (String) productoModificarView.getCbxOpciones().getSelectedItem();
                switch (tipo) {
                    case "Modificar Nombre":
                        productoModificarView.getLblMensaje().setText("Nuevo Nombre");
                        break;
                    case "Modificar Codigo":
                        productoModificarView.getLblMensaje().setText("Nuevo Codigo");
                        break;
                    case "Modificar Precio":
                        productoModificarView.getLblMensaje().setText("Nuevo Precio");
                        break;
                    default:
                        productoModificarView.mostrarMensaje("Ingrese el tipo de modificación");
                }
            }
        });

        // Botón modificar producto
        productoModificarView.getBtnModificar().addActionListener(e -> {
            int codigoOriginal = Integer.parseInt(productoModificarView.getLblCodigo().getText());
            String tipo = (String) productoModificarView.getCbxOpciones().getSelectedItem();
            Producto productoOriginal = productoDAO.buscarPorCodigo(codigoOriginal);
            Producto productoNuevo = new Producto();

            switch (tipo) {
                case "Modificar Nombre":
                    productoNuevo.setCodigo(productoOriginal.getCodigo());
                    productoNuevo.setNombre(productoModificarView.getTxtModificar().getText());
                    productoNuevo.setPrecio(productoOriginal.getPrecio());
                    break;
                case "Modificar Codigo":
                    productoNuevo.setCodigo(Integer.parseInt(productoModificarView.getTxtModificar().getText()));
                    productoNuevo.setNombre(productoOriginal.getNombre());
                    productoNuevo.setPrecio(productoOriginal.getPrecio());
                    break;
                case "Modificar Precio":
                    productoNuevo.setCodigo(productoOriginal.getCodigo());
                    productoNuevo.setNombre(productoOriginal.getNombre());
                    productoNuevo.setPrecio(Double.parseDouble(productoModificarView.getTxtModificar().getText()));
                    break;
                default:
                    productoModificarView.mostrarMensaje("Ingrese el tipo de modificación");
                    return;
            }
            // Reemplazar producto en DAO
            productoDAO.eliminar(codigoOriginal);
            productoDAO.crear(productoNuevo);

            productoModificarView.mostrarMensaje("Producto modificado correctamente");
            productoModificarView.getTxtModificar().setText("");
            productoModificarView.getCbxOpciones().setEnabled(false);
            productoModificarView.getLblCodigo().setText("");
            productoModificarView.getLblNombre().setText("");
            productoModificarView.getLblPrecio().setText("");
            productoModificarView.getTxtCodigo().setText("");
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
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
            subtotal += (double) modelo.getValueAt(i, 3); // columna "Total"
        }
        double iva = subtotal * 0.12;
        double total = subtotal + iva;

        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", subtotal));
        carritoAnadirView.getTxtIva().setText(String.format("%.2f", iva));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", total));
    }
}
