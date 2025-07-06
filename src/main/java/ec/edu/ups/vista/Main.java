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

                        mensajes.verificarClavesFaltantes("claves_requeridas.txt");

                        // Controladores
                        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, usuarioController);

                        // Vistas de producto
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoModificarView productoModificarView = new ProductoModificarView();

                        // Vistas de carrito
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(carritoController);
                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(carritoController, mensajes);
                        CarritoModificarView carritoModificarView = new CarritoModificarView(carritoController);
                        CarritoListarView carritoListarView = new CarritoListarView(carritoController);
                        CarritoListarMisView carritoListarMisView = new CarritoListarMisView(carritoController);

                        // Controlador de producto
                        new ProductoController(
                                productoDAO,
                                productoAnadirView,
                                productoListaView,
                                productoEliminarView,
                                productoModificarView,
                                carritoAnadirView,
                                carritoDAO,
                                carritoController
                        );

                        // Menú principal
                        MenuPrincipalView principalView = new MenuPrincipalView(mensajes, carritoController);
                        principalView.setVisible(true);
                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                        // Configuración de internacionalización y vistas en el controlador de usuario
                        usuarioController.setInternacionalizacionYVistas(mensajes, principalView);

                        // Vistas de usuario en el menú
                        principalView.getMenuItemCrearUsuario().addActionListener(ev -> usuarioController.mostrarVistaCrearUsuario());
                        principalView.getMenuItemEliminarUsuario().addActionListener(ev -> usuarioController.mostrarVistaEliminarUsuario());
                        principalView.getMenuItemModificarUsuario().addActionListener(ev -> usuarioController.mostrarVistaModificarUsuario());
                        principalView.getMenuItemListarUsuarios().addActionListener(ev -> usuarioController.mostrarVistaListarUsuarios());

                        // Deshabilitar funciones admin si es usuario normal
                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                            principalView.ocultarMenusAdministrador();
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
                        principalView.getMenuItemListarMisCarritos().addActionListener(ev -> mostrarVentana(carritoListarMisView, principalView));

                        // Actualizar datos personales
                        principalView.getMenuItemActualizarDatos().addActionListener(eg -> usuarioController.mostrarVistaActualizarUsuario());

                        // Idiomas
                        principalView.getMenuItemIdiomaEspanol().addActionListener(t -> principalView.cambiarIdioma("es", "EC"));
                        principalView.getMenuItemIdiomaIngles().addActionListener(t -> principalView.cambiarIdioma("en", "US"));
                        principalView.getMenuItemIdiomaFrances().addActionListener(t -> principalView.cambiarIdioma("fr", "FR"));

                        // Salir
                        principalView.getMenuItemSalir().addActionListener(r -> {
                            int opcion = JOptionPane.showConfirmDialog(principalView, "¿Deseas salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
                            if (opcion == JOptionPane.YES_OPTION) System.exit(0);
                        });

                        // Cerrar sesión
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
        JDesktopPane escritorio = principalView.getjDesktopPane();

        for (JInternalFrame frame : escritorio.getAllFrames()) {
            frame.setVisible(false);
            escritorio.remove(frame);
        }

        escritorio.add(ventana);
        ventana.setVisible(true);
        ventana.toFront();
    }

}
