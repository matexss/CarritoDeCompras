package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class MenuPrincipalView extends JFrame {

    private JMenuBar menuBar;
    private JDesktopPane jDesktopPane;

    // Menús
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuUsuario;
    private JMenu menuIdioma;
    private JMenu menuSesion;

    // Ítems Producto
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemBuscarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemEliminarProducto;

    // Ítems Carrito
    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemModificarCarrito;
    private JMenuItem menuItemListarCarritos;
    private JMenuItem menuItemListarMisCarritos;

    // Ítems Usuario
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemModificarUsuario;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemActualizarDatos;

    // Ítems Sesión
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;

    // Ítems Idioma
    private JMenuItem menuItemIdiomaEspanol;
    private JMenuItem menuItemIdiomaIngles;
    private JMenuItem menuItemIdiomaFrances;

    // Otros
    private MensajeInternacionalizacionHandler mensajes;
    private CarritoController carritoController;

    public MenuPrincipalView(MensajeInternacionalizacionHandler mensajes, CarritoController carritoController) {
        this.mensajes = mensajes;
        this.carritoController = carritoController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión - Carrito de Compras");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        jDesktopPane = new JDesktopPane();
        setContentPane(jDesktopPane);

        menuBar = new JMenuBar();

        // Menús
        menuProducto = new JMenu("Productos");
        menuCarrito = new JMenu("Carritos");
        menuUsuario = new JMenu("Usuarios");
        menuIdioma = new JMenu("Idioma");
        menuSesion = new JMenu("Sesión");

        // Productos
        menuItemCrearProducto = new JMenuItem("Crear Producto");
        menuItemBuscarProducto = new JMenuItem("Buscar Producto");
        menuItemActualizarProducto = new JMenuItem("Actualizar Producto");
        menuItemEliminarProducto = new JMenuItem("Eliminar Producto");

        // Carrito
        menuItemCrearCarrito = new JMenuItem("Crear Carrito");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemModificarCarrito = new JMenuItem("Modificar Carrito");
        menuItemListarCarritos = new JMenuItem("Listar Todos los Carritos");
        menuItemListarMisCarritos = new JMenuItem("Listar Mis Carritos");

        // Usuarios
        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");
        menuItemModificarUsuario = new JMenuItem("Modificar Usuario");
        menuItemListarUsuarios = new JMenuItem("Listar Usuarios");
        menuItemActualizarDatos = new JMenuItem("Actualizar Mis Datos");

        // Idiomas
        menuItemIdiomaEspanol = new JMenuItem("Español");
        menuItemIdiomaIngles = new JMenuItem("Inglés");
        menuItemIdiomaFrances = new JMenuItem("Francés");

        // Sesión
        menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
        menuItemSalir = new JMenuItem("Salir");

        // Agregar ítems a sus menús
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemEliminarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemModificarCarrito);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemListarMisCarritos);

        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemModificarUsuario);
        menuUsuario.add(menuItemListarUsuarios);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemActualizarDatos);

        menuIdioma.add(menuItemIdiomaEspanol);
        menuIdioma.add(menuItemIdiomaIngles);
        menuIdioma.add(menuItemIdiomaFrances);

        menuSesion.add(menuItemCerrarSesion);
        menuSesion.add(menuItemSalir);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSesion);

        setJMenuBar(menuBar);
    }

    // --- Getters para conectar eventos desde Main ---
    public JMenuItem getMenuItemCrearProducto() { return menuItemCrearProducto; }
    public JMenuItem getMenuItemBuscarProducto() { return menuItemBuscarProducto; }
    public JMenuItem getMenuItemActualizarProducto() { return menuItemActualizarProducto; }
    public JMenuItem getMenuItemEliminarProducto() { return menuItemEliminarProducto; }

    public JMenuItem getMenuItemCrearCarrito() { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public JMenuItem getMenuItemModificarCarrito() { return menuItemModificarCarrito; }
    public JMenuItem getMenuItemListarCarritos() { return menuItemListarCarritos; }
    public JMenuItem getMenuItemListarMisCarritos() { return menuItemListarMisCarritos; }

    public JMenuItem getMenuItemCrearUsuario() { return menuItemCrearUsuario; }
    public JMenuItem getMenuItemEliminarUsuario() { return menuItemEliminarUsuario; }
    public JMenuItem getMenuItemModificarUsuario() { return menuItemModificarUsuario; }
    public JMenuItem getMenuItemListarUsuarios() { return menuItemListarUsuarios; }
    public JMenuItem getMenuItemActualizarDatos() { return menuItemActualizarDatos; }

    public JMenuItem getMenuItemCerrarSesion() { return menuItemCerrarSesion; }
    public JMenuItem getMenuItemSalir() { return menuItemSalir; }

    public JMenuItem getMenuItemIdiomaEspanol() { return menuItemIdiomaEspanol; }
    public JMenuItem getMenuItemIdiomaIngles() { return menuItemIdiomaIngles; }
    public JMenuItem getMenuItemIdiomaFrances() { return menuItemIdiomaFrances; }

    public JDesktopPane getjDesktopPane() { return jDesktopPane; }

    // --- Métodos para manipular el menú según el rol ---
    public void deshabilitarMenusAdministrador() {
        menuProducto.setEnabled(false);
        menuUsuario.setEnabled(false);
        menuItemListarCarritos.setEnabled(false);
        menuItemEliminarCarrito.setEnabled(false);
        menuItemModificarCarrito.setEnabled(false);
    }

    public void ocultarMenusAdministrador() {
        menuProducto.setVisible(false);
        menuUsuario.setVisible(false);
        menuItemListarCarritos.setVisible(false);
        menuItemEliminarCarrito.setVisible(false);
        menuItemModificarCarrito.setVisible(false);
    }

    // --- Método para cambiar idioma ---
    public void cambiarIdioma(String lang, String country) {
        this.mensajes.cambiarLocale(new Locale(lang, country));
        JOptionPane.showMessageDialog(this, mensajes.getMensaje("idioma.cambiado"));
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
