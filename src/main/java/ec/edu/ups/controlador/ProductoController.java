package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.util.List;

/**
 * Controlador para gestionar productos y su integración con el carrito de compras.
 * Coordina las vistas de productos (crear, listar, eliminar, modificar)
 * y permite añadir productos a un carrito.
 */
public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private final CarritoController carritoController;

    /**
     * Constructor principal que inicializa las vistas, DAOs y el controlador de carrito.
     * @param productoDAO DAO de productos
     * @param productoAnadirView Vista para añadir productos
     * @param productoListaView Vista para listar productos
     * @param productoEliminarView Vista para eliminar productos
     * @param productoModificarView Vista para modificar productos
     * @param carritoAnadirView Vista para añadir productos al carrito
     * @param carritoDAO DAO de carritos
     * @param carritoController Controlador del carrito
     */
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

    /**
     * Configura todos los listeners de eventos de las vistas relacionadas a productos y carrito.
     */
    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());
        productoListaView.getBtnListar().addActionListener(e -> mostrarProductos());

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

        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());

        productoModificarView.getBuscarButton().addActionListener(e -> buscarParaModificar());
        productoModificarView.getBtnModificar().addActionListener(e -> modificarProducto());

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

        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoParaCarrito());
        carritoAnadirView.getBtnAñadir().addActionListener(e -> agregarProductoACarrito());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
    }

    /**
     * Guarda un nuevo producto después de validar los datos ingresados.
     */
    private void guardarProducto() {
        // ...
    }

    /**
     * Busca productos por nombre y actualiza la vista.
     */
    private void buscarProducto() {
        // ...
    }

    /**
     * Muestra todos los productos en las vistas correspondientes.
     */
    private void mostrarProductos() {
        // ...
    }

    /**
     * Elimina un producto si se confirma su existencia y la eliminación.
     */
    private void eliminarProducto() {
        // ...
    }

    /**
     * Busca un producto específico para modificar sus datos.
     */
    private void buscarParaModificar() {
        // ...
    }

    /**
     * Modifica un producto según la opción seleccionada (nombre, código o precio).
     */
    private void modificarProducto() {
        // ...
    }

    /**
     * Limpia los campos de la vista de modificación de producto.
     */
    private void limpiarVistaModificar() {
        // ...
    }

    /**
     * Busca un producto por código para añadirlo al carrito.
     */
    private void buscarProductoParaCarrito() {
        // ...
    }

    /**
     * Añade un producto al carrito con la cantidad especificada.
     */
    private void agregarProductoACarrito() {
        // ...
    }

    /**
     * Guarda el carrito actual con los productos añadidos.
     */
    private void guardarCarrito() {
        // ...
    }

    /**
     * Calcula el subtotal, IVA y total de la compra mostrada en la tabla.
     * @param modelo Modelo de tabla con los productos añadidos.
     */
    private void calcularTotales(DefaultTableModel modelo) {
        // ...
    }

    /**
     * Muestra un mensaje emergente en pantalla.
     * @param mensaje Texto a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
