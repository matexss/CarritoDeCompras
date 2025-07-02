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
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            LoginView loginView = new LoginView();
            loginView.setVisible(true);

            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAutenticado != null) {
                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();

                        MenuPrincipalView principalView = new MenuPrincipalView();

                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoModificarView productoModificarView = new ProductoModificarView();
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();

                        new ProductoController(productoDAO, productoAnadirView, productoListaView, productoEliminarView, productoModificarView, carritoAnadirView, carritoDAO);
                        new CarritoController(carritoDAO, productoDAO, carritoAnadirView);

                        principalView.setVisible(true);
                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());

                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        principalView.getMenuItemCrearProducto().addActionListener(ev -> {
                            mostrarVentana(productoAnadirView, principalView);
                        });

                        principalView.getMenuItemBuscarProducto().addActionListener(ev -> {
                            mostrarVentana(productoListaView, principalView);
                        });

                        principalView.getMenuItemEliminarProducto().addActionListener(ev -> {
                            mostrarVentana(productoEliminarView, principalView);
                        });

                        principalView.getMenuItemActualizarProducto().addActionListener(ev -> {
                            mostrarVentana(productoModificarView, principalView);
                        });

                        principalView.getMenuItemCrearCarrito().addActionListener(ev -> {
                            mostrarVentana(carritoAnadirView, principalView);
                        });

                        principalView.getMenuItemIdiomaEspanol().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                principalView.cambiarIdioma("es", "EC");
                            }
                        });

                        principalView.getMenuItemIdiomaIngles().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                principalView.cambiarIdioma("en", "US");
                            }
                        });

                        principalView.getMenuItemIdiomaFrances().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                principalView.cambiarIdioma("fr", "FR");
                            }
                        });

                        // Botón Salir
                        principalView.getMenuItemSalir().addActionListener(r -> {
                            int opcion = javax.swing.JOptionPane.showConfirmDialog(principalView, "¿Deseas salir de la aplicación?", "Salir", javax.swing.JOptionPane.YES_NO_OPTION);
                            if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                                System.exit(0);
                            }
                        });

                        principalView.getMenuItemCerrarSesion().addActionListener(r -> {
                            int opcion = javax.swing.JOptionPane.showConfirmDialog(principalView, "¿Deseas cerrar sesión?", "Cerrar Sesión", javax.swing.JOptionPane.YES_NO_OPTION);
                            if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                                principalView.dispose(); // Cierra la ventana principal
                                LoginView nuevoLogin = new LoginView(); // Abre login nuevamente
                                nuevoLogin.setVisible(true);
                                new UsuarioController(usuarioDAO, nuevoLogin); // Vuelve a conectar el controlador
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
