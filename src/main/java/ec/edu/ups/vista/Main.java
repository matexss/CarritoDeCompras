package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.servicio.CarritoServiceImpl;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciarAplicacion);

    }

    public static void iniciarAplicacion() {
        String[] opciones = {"Memoria", "Archivo Texto", "Archivo Binario"};
        int seleccion = JOptionPane.showOptionDialog(null,
                "Seleccione el tipo de almacenamiento",
                "Tipo de Almacenamiento",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        UsuarioDAO usuarioDAO;
        ProductoDAO productoDAO;
        CarritoDAO carritoDAO;

        if (seleccion == 1 || seleccion == 2) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int opcionRuta = chooser.showOpenDialog(null);
            if (opcionRuta != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "No se seleccionó directorio. Cerrando aplicación.");
                System.exit(0);
            }
            File carpeta = chooser.getSelectedFile();

            if (seleccion == 1) { // Texto
                usuarioDAO = new UsuarioDAOArchivoTexto(new File(carpeta, "usuarios.txt").getAbsolutePath());
                productoDAO = new ProductoDAOArchivoTexto(new File(carpeta, "productos.txt").getAbsolutePath());
                carritoDAO = new CarritoDAOArchivoTexto(new File(carpeta, "carritos.txt").getAbsolutePath());
            } else { // Binario
                usuarioDAO = new UsuarioDAOArchivoBinario(new File(carpeta, "usuarios.bin").getAbsolutePath());
                productoDAO = new ProductoDAOArchivoBinario(new File(carpeta, "productos.bin").getAbsolutePath());
                carritoDAO = new CarritoDAOArchivoBinario(new File(carpeta, "carritos.bin").getAbsolutePath());
            }

        } else {
            usuarioDAO = new UsuarioDAOMemoria();
            productoDAO = new ProductoDAOMemoria();
            carritoDAO = new CarritoDAOMemoria();
        }

        MensajeInternacionalizacionHandler mensajes = new MensajeInternacionalizacionHandler("es", "EC");
        mensajes.verificarClavesFaltantes("claves_requeridas.txt");

        // 🔁 Versión corregida del LoginView
        LoginView loginView = new LoginView(mensajes);
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

                    principalView.getMenuItemCrearUsuario().addActionListener(ev -> usuarioController.mostrarVistaCrearUsuario());
                    principalView.getMenuItemEliminarUsuario().addActionListener(ev -> usuarioController.mostrarVistaEliminarUsuario());
                    principalView.getMenuItemModificarUsuario().addActionListener(ev -> usuarioController.mostrarVistaModificarUsuario());
                    principalView.getMenuItemListarUsuarios().addActionListener(ev -> usuarioController.mostrarVistaListarUsuarios());
                    principalView.getMenuItemActualizarDatos().addActionListener(ev -> usuarioController.mostrarVistaActualizarUsuario());

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
                    principalView.getMenuItemListarCarritos().addActionListener(ev -> {
                        if (usuarioAutenticado.getRol().equals(Rol.ADMINISTRADOR)) {
                            mostrarVentana(carritoListarView, principalView);
                        } else {
                            mostrarVentana(carritoListarMisView, principalView);
                        }
                    });
                    principalView.getMenuItemListarMisCarritos().addActionListener(ev -> mostrarVentana(carritoListarMisView, principalView));

                    principalView.getMenuItemIdiomaEspanol().addActionListener(ev -> principalView.cambiarIdioma("es", "EC"));
                    principalView.getMenuItemIdiomaIngles().addActionListener(ev -> principalView.cambiarIdioma("en", "US"));
                    principalView.getMenuItemIdiomaFrances().addActionListener(ev -> principalView.cambiarIdioma("fr", "FR"));

                    principalView.getMenuItemSalir().addActionListener(ev -> {
                        int opcion = JOptionPane.showConfirmDialog(principalView, mensajes.get("menu.salir.confirmacion"), mensajes.get("menu.salir.titulo"), JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) System.exit(0);
                    });

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
