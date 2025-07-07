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
import ec.edu.ups.vista.MenuPrincipalView;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciarAplicacion);
    }

    public static void iniciarAplicacion() {
        MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");
        mensajes.verificarClavesFaltantes("claves_requeridas.txt");

        LoginView loginView = new LoginView();
        UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView, mensajes);
        loginView.setVisible(true);

        loginView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                if (usuarioAutenticado != null) {
                    CarritoServiceImpl carritoService = new CarritoServiceImpl();
                    CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, usuarioController);

                    ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensajes);
                    ProductoListaView productoListaView = new ProductoListaView(mensajes);
                    ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensajes);
                    ProductoModificarView productoModificarView = new ProductoModificarView(mensajes);

                    CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensajes, carritoController);
                    CarritoEliminarView carritoEliminarView = new CarritoEliminarView(carritoController, mensajes);
                    CarritoModificarView carritoModificarView = new CarritoModificarView(carritoController, mensajes);
                    CarritoListarView carritoListarView = new CarritoListarView(carritoController, mensajes);
                    CarritoListarMisView carritoListarMisView = new CarritoListarMisView(carritoController, mensajes);

                    new ProductoController(productoDAO, productoAnadirView, productoListaView, productoEliminarView, productoModificarView, carritoAnadirView, carritoDAO, carritoController);

                    MenuPrincipalView principalView = new MenuPrincipalView(mensajes, carritoController);
                    principalView.setVisible(true);
                    principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                    usuarioController.setInternacionalizacionYVistas(mensajes, principalView);

                    // Configuración de menús
                    principalView.getMenuItemCrearUsuario().addActionListener(ev -> usuarioController.mostrarVistaCrearUsuario());
                    principalView.getMenuItemEliminarUsuario().addActionListener(ev -> usuarioController.mostrarVistaEliminarUsuario());
                    principalView.getMenuItemModificarUsuario().addActionListener(ev -> usuarioController.mostrarVistaModificarUsuario());
                    principalView.getMenuItemListarUsuarios().addActionListener(ev -> usuarioController.mostrarVistaListarUsuarios());
                    principalView.getMenuItemActualizarDatos().addActionListener(ev -> usuarioController.mostrarVistaActualizarUsuario());

                    if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                        principalView.deshabilitarMenusAdministrador();
                        principalView.ocultarMenusAdministrador();
                    }

                    // Productos
                    principalView.getMenuItemCrearProducto().addActionListener(ev -> mostrarVentana(productoAnadirView, principalView));
                    principalView.getMenuItemBuscarProducto().addActionListener(ev -> mostrarVentana(productoListaView, principalView));
                    principalView.getMenuItemEliminarProducto().addActionListener(ev -> mostrarVentana(productoEliminarView, principalView));
                    principalView.getMenuItemActualizarProducto().addActionListener(ev -> mostrarVentana(productoModificarView, principalView));

                    // Carritos
                    principalView.getMenuItemCrearCarrito().addActionListener(ev -> mostrarVentana(carritoAnadirView, principalView));
                    principalView.getMenuItemEliminarCarrito().addActionListener(ev -> mostrarVentana(carritoEliminarView, principalView));
                    principalView.getMenuItemModificarCarrito().addActionListener(ev -> mostrarVentana(carritoModificarView, principalView));
                    principalView.getMenuItemListarCarritos().addActionListener(ev -> {
                        if (usuarioAutenticado.getRol() == Rol.ADMINISTRADOR) {
                            mostrarVentana(carritoListarView, principalView);
                        } else {
                            mostrarVentana(carritoListarMisView, principalView);
                        }
                    });
                    principalView.getMenuItemListarMisCarritos().addActionListener(ev -> mostrarVentana(carritoListarMisView, principalView));

                    // Idioma
                    principalView.getMenuItemIdiomaEspanol().addActionListener(ev -> principalView.cambiarIdioma("es", "EC"));
                    principalView.getMenuItemIdiomaIngles().addActionListener(ev -> principalView.cambiarIdioma("en", "US"));
                    principalView.getMenuItemIdiomaFrances().addActionListener(ev -> principalView.cambiarIdioma("fr", "FR"));

                    // Salir
                    principalView.getMenuItemSalir().addActionListener(ev -> {
                        int opcion = JOptionPane.showConfirmDialog(principalView, mensajes.get("menu.salir.confirmacion"), mensajes.get("menu.salir.titulo"), JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) System.exit(0);
                    });

                    // Cerrar sesión
                    principalView.getMenuItemCerrarSesion().addActionListener(ev -> {
                        int opcion = JOptionPane.showConfirmDialog(principalView, mensajes.get("menu.salir.cerrarConfirmacion"), mensajes.get("menu.salir.titulo"), JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            principalView.dispose();
                            iniciarAplicacion();
                        }
                    });
                }
            }
        });
    }

    private static void mostrarVentana(JInternalFrame ventana, MenuPrincipalView principalView) {
        if (ventana instanceof CarritoListarView listar) {
            listar.cargarCarritos();
        } else if (ventana instanceof CarritoListarMisView mis) {
            mis.cargarCarritos();
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
