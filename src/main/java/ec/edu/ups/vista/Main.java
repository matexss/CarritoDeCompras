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

    // ✅ DAOs únicos compartidos durante toda la ejecución
    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> iniciarAplicacion());
    }

    public static void iniciarAplicacion() {
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
        UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

        loginView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                if (usuarioAutenticado != null) {
                    CarritoServiceImpl carritoService = new CarritoServiceImpl();

                    MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");
                    mensajes.verificarClavesFaltantes("claves_requeridas.txt");

                    CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, usuarioController);

                    ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                    ProductoListaView productoListaView = new ProductoListaView();
                    ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                    ProductoModificarView productoModificarView = new ProductoModificarView();

                    CarritoAnadirView carritoAnadirView = new CarritoAnadirView(carritoController);
                    CarritoEliminarView carritoEliminarView = new CarritoEliminarView(carritoController, mensajes);
                    CarritoModificarView carritoModificarView = new CarritoModificarView(carritoController);
                    CarritoListarView carritoListarView = new CarritoListarView(carritoController);
                    CarritoListarMisView carritoListarMisView = new CarritoListarMisView(carritoController);

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

                    MenuPrincipalView principalView = new MenuPrincipalView(mensajes, carritoController);
                    principalView.setVisible(true);
                    principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                    usuarioController.setInternacionalizacionYVistas(mensajes, principalView);

                    principalView.getMenuItemCrearUsuario().addActionListener(ev -> usuarioController.mostrarVistaCrearUsuario());
                    principalView.getMenuItemEliminarUsuario().addActionListener(ev -> usuarioController.mostrarVistaEliminarUsuario());
                    principalView.getMenuItemModificarUsuario().addActionListener(ev -> usuarioController.mostrarVistaModificarUsuario());
                    principalView.getMenuItemListarUsuarios().addActionListener(ev -> usuarioController.mostrarVistaListarUsuarios());

                    if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                        principalView.deshabilitarMenusAdministrador();
                        principalView.ocultarMenusAdministrador();
                    }

                    principalView.getMenuItemCrearProducto().addActionListener(ev -> mostrarVentana(productoAnadirView, principalView));
                    principalView.getMenuItemBuscarProducto().addActionListener(ev -> mostrarVentana(productoListaView, principalView));
                    principalView.getMenuItemEliminarProducto().addActionListener(ev -> mostrarVentana(productoEliminarView, principalView));
                    principalView.getMenuItemActualizarProducto().addActionListener(ev -> mostrarVentana(productoModificarView, principalView));

                    principalView.getMenuItemCrearCarrito().addActionListener(ev -> mostrarVentana(carritoAnadirView, principalView));
                    principalView.getMenuItemEliminarCarrito().addActionListener(ev -> mostrarVentana(carritoEliminarView, principalView));
                    principalView.getMenuItemModificarCarrito().addActionListener(ev -> mostrarVentana(carritoModificarView, principalView));
                    if (usuarioAutenticado.getRol().equals(Rol.ADMINISTRADOR)) {
                        principalView.getMenuItemListarCarritos().addActionListener(ev -> mostrarVentana(carritoListarView, principalView));
                    } else {
                        principalView.getMenuItemListarCarritos().addActionListener(ev -> mostrarVentana(carritoListarMisView, principalView));
                    }


                    principalView.getMenuItemActualizarDatos().addActionListener(eg -> usuarioController.mostrarVistaActualizarUsuario());

                    principalView.getMenuItemIdiomaEspanol().addActionListener(t -> principalView.cambiarIdioma("es", "EC"));
                    principalView.getMenuItemIdiomaIngles().addActionListener(t -> principalView.cambiarIdioma("en", "US"));
                    principalView.getMenuItemIdiomaFrances().addActionListener(t -> principalView.cambiarIdioma("fr", "FR"));

                    principalView.getMenuItemSalir().addActionListener(r -> {
                        int opcion = JOptionPane.showConfirmDialog(principalView, "¿Deseas salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) System.exit(0);
                    });

                    principalView.getMenuItemCerrarSesion().addActionListener(r -> {
                        int opcion = JOptionPane.showConfirmDialog(principalView, "¿Deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            principalView.dispose();
                            iniciarAplicacion(); // Reinicia con los mismos DAOs
                        }
                    });

                    principalView.getMenuItemListarMisCarritos().addActionListener(ev ->
                            mostrarVentana(carritoListarMisView, principalView));

                }
            }
        });
    }

    private static void mostrarVentana(JInternalFrame ventana, MenuPrincipalView principalView) {
        if (ventana instanceof ec.edu.ups.vista.CarritoListarView listar) {
            listar.cargarCarritos();          // admin
        } else if (ventana instanceof ec.edu.ups.vista.CarritoListarMisView mis) {
            mis.cargarCarritos();             // usuario
        }

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
