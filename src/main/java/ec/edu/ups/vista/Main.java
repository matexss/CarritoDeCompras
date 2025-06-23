package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.JInternalFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // DAOs
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            // Vistas internas
            ProductoAnadirView productoAnadirView = new ProductoAnadirView();
            ProductoListaView productoListaView = new ProductoListaView();
            ProductoEliminarView productoEliminarView = new ProductoEliminarView();
            ProductoModificarView productoModificarView = new ProductoModificarView();
            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

            // Vista principal
            MenuPrincipalView principalView = new MenuPrincipalView();
            principalView.setVisible(true);

            // Controlador
            new ProductoController(
                    productoDAO,
                    productoAnadirView,
                    productoListaView,
                    productoEliminarView,
                    productoModificarView,
                    carritoAnadirView,
                    carritoDAO
            );

            // Listeners del menú para mostrar vistas internas (una a la vez)
            principalView.getMenuItemCrearProducto().addActionListener(e -> mostrarVentana(productoAnadirView, principalView));
            principalView.getMenuItemBuscarProducto().addActionListener(e -> mostrarVentana(productoListaView, principalView));
            principalView.getMenuItemEliminarProducto().addActionListener(e -> mostrarVentana(productoEliminarView, principalView));
            principalView.getMenuItemActualizarProducto().addActionListener(e -> mostrarVentana(productoModificarView, principalView));
            principalView.getMenuItemCrearCarrito().addActionListener(e -> mostrarVentana(carritoAnadirView, principalView));
        });
    }

    private static void mostrarVentana(JInternalFrame ventana, MenuPrincipalView principalView) {
        // Cerrar todas las ventanas abiertas antes de abrir la nueva
        for (JInternalFrame frame : principalView.getjDesktopPane().getAllFrames()) {
            frame.dispose(); // cierra la ventana interna
        }

        // Agregar y mostrar la nueva ventana
        principalView.getjDesktopPane().add(ventana);
        ventana.setVisible(true);
        ventana.toFront();
    }
}
