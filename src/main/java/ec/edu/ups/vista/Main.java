package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.servicio.CarritoServiceImpl;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // DAO de usuarios
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();

            // Login
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAutenticado != null) {
                        // DAOs
                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();

                        // Servicios
                        CarritoServiceImpl carritoService = new CarritoServiceImpl();

                        // Internacionalización por defecto
                        MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");

                        // Controladores
                        // Nota: paso productoDAO también al carritoController para buscar productos
                        CarritoController carritoController = new CarritoController(carritoService, productoDAO, carritoDAO, usuarioAutenticado);

                        // Vistas de producto
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoModificarView productoModificarView = new ProductoModificarView();

                        // Vistas de carrito
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(carritoController);
                        carritoAnadirView.getBtnGuardar().addActionListener(n -> {
                            System.out.println("EVENTO DIRECTO DESDE MAIN");
                            JOptionPane.showMessageDialog(null, "Evento desde Main");
                        });

                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(carritoController, mensajes);
                        CarritoModificarView carritoModificarView = new CarritoModificarView(carritoController);
                        CarritoListarMisView carritoListarView = new CarritoListarMisView(carritoController);

                        // Controlador de producto —> AÑADIR carritoController como parámetro
                        new ProductoController(
                                productoDAO,
                                productoAnadirView,
                                productoListaView,
                                productoEliminarView,
                                productoModificarView,
                                carritoAnadirView,
                                carritoDAO,
                                carritoController   // <== Aquí lo agregamos para que el productoController pueda usarlo
                        );

                        // Menú principal
                        MenuPrincipalView principalView = new MenuPrincipalView(mensajes, carritoController);
                        principalView.setVisible(true);
                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                        // Si el usuario NO es administrador, deshabilitar funciones de admin
                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        // CONEXIÓN DE MENÚ CON VISTAS

                        // Productos
                        principalView.getMenuItemCrearProducto().addActionListener(ev -> mostrarVentana(productoAnadirView, principalView));
                        principalView.getMenuItemBuscarProducto().addActionListener(ev -> mostrarVentana(productoListaView, principalView));
                        principalView.getMenuItemEliminarProducto().addActionListener(ev -> mostrarVentana(productoEliminarView, principalView));
                        principalView.getMenuItemActualizarProducto().addActionListener(ev -> mostrarVentana(productoModificarView, principalView));

                        // Carritos
                        principalView.getMenuItemCrearCarrito().addActionListener(ev -> mostrarVentana(carritoAnadirView, principalView));
                        principalView.getMenuItemEliminarCarrito().addActionListener(ev -> mostrarVentana(carritoEliminarView, principalView));
                        principalView.getMenuItemModificarCarrito().addActionListener(ev -> mostrarVentana(carritoModificarView, principalView));
                        principalView.getMenuItemListarCarritos().addActionListener(ev -> mostrarVentana(carritoListarView, principalView));

                        // Idiomas
                        principalView.getMenuItemIdiomaEspanol().addActionListener(t -> principalView.cambiarIdioma("es", "EC"));
                        principalView.getMenuItemIdiomaIngles().addActionListener(t -> principalView.cambiarIdioma("en", "US"));
                        principalView.getMenuItemIdiomaFrances().addActionListener(t -> principalView.cambiarIdioma("fr", "FR"));

                        // Cerrar sesión y salir
                        principalView.getMenuItemSalir().addActionListener(r -> {
                            int opcion = JOptionPane.showConfirmDialog(principalView, "¿Deseas salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
                            if (opcion == JOptionPane.YES_OPTION) System.exit(0);
                        });
                        carritoAnadirView.getBtnGuardar().addActionListener(p -> {
                            JOptionPane.showMessageDialog(null, "PRUEBA DIRECTA DESDE MAIN");
                        });


                        principalView.getMenuItemCerrarSesion().addActionListener(r -> {
                            int opcion = JOptionPane.showConfirmDialog(principalView, "¿Deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                            if (opcion == JOptionPane.YES_OPTION) {
                                principalView.dispose();
                                LoginView nuevoLogin = new LoginView();
                                nuevoLogin.setVisible(true);
                                new UsuarioController(usuarioDAO, nuevoLogin);
                            }
                        });
                    }
                }
            });
        });
    }

    private static void mostrarVentana(JInternalFrame ventana, MenuPrincipalView principalView) {
        for (JInternalFrame frame : principalView.getjDesktopPane().getAllFrames()) {
            frame.dispose();
        }
        principalView.getjDesktopPane().add(ventana);
        ventana.setVisible(true);
        ventana.toFront();
    }
}
