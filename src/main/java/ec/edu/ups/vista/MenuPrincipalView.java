package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.ActualizableConIdioma;

import javax.swing.*;

public class MenuPrincipalView extends JFrame {

    // ──────────────────── Componentes ────────────────────
    private final JMenuBar menuBar = new JMenuBar();
    private final JDesktopPane jDesktopPane = new MiJDesktopPane();

    private final JMenu menuProducto  = new JMenu();
    private final JMenu menuCarrito   = new JMenu();
    private final JMenu menuUsuario   = new JMenu();
    private final JMenu menuIdioma    = new JMenu();
    private final JMenu menuSesion    = new JMenu();

    private final JMenuItem menuItemCrearProducto      = new JMenuItem();
    private final JMenuItem menuItemBuscarProducto     = new JMenuItem();
    private final JMenuItem menuItemActualizarProducto = new JMenuItem();
    private final JMenuItem menuItemEliminarProducto   = new JMenuItem();

    private final JMenuItem menuItemCrearCarrito       = new JMenuItem();
    private final JMenuItem menuItemEliminarCarrito    = new JMenuItem();
    private final JMenuItem menuItemModificarCarrito   = new JMenuItem();
    private final JMenuItem menuItemListarCarritos     = new JMenuItem();
    private final JMenuItem menuItemListarMisCarritos  = new JMenuItem();

    private final JMenuItem menuItemCrearUsuario       = new JMenuItem();
    private final JMenuItem menuItemEliminarUsuario    = new JMenuItem();
    private final JMenuItem menuItemModificarUsuario   = new JMenuItem();
    private final JMenuItem menuItemListarUsuarios     = new JMenuItem();
    private final JMenuItem menuItemActualizarDatos    = new JMenuItem();

    private final JMenuItem menuItemCerrarSesion       = new JMenuItem();
    private final JMenuItem menuItemSalir              = new JMenuItem();

    private final JMenuItem menuItemIdiomaEspanol      = new JMenuItem();
    private final JMenuItem menuItemIdiomaIngles       = new JMenuItem();
    private final JMenuItem menuItemIdiomaFrances      = new JMenuItem();

    // ──────────────────── Soporte ────────────────────
    private final MensajeInternacionalizacionHandler mensajes;
    private final CarritoController carritoController;

    // ──────────────────── Constructor ────────────────────
    public MenuPrincipalView(MensajeInternacionalizacionHandler mensajes, CarritoController carritoController) {
        this.mensajes = mensajes;
        this.carritoController = carritoController;
        initComponents();
        configurarIdiomaListeners();
    }

    // ──────────────────── Inicialización ────────────────────
    private void initComponents() {
        setTitle("Sistema de Gestión - Carrito de Compras");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(jDesktopPane);

        // Armar menús
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

        actualizarTextos();
    }

    // ──────────────────── Internacionalización ────────────────────
    private void configurarIdiomaListeners() {
        menuItemIdiomaEspanol.addActionListener(e -> cambiarIdioma("es", "EC"));
        menuItemIdiomaIngles .addActionListener(e -> cambiarIdioma("en", "US"));
        menuItemIdiomaFrances.addActionListener(e -> cambiarIdioma("fr", "FR"));
    }

    public void cambiarIdioma(String lang, String country) {
        mensajes.cambiarIdioma(lang, country);
        actualizarTextos();
        for (JInternalFrame frame : jDesktopPane.getAllFrames()) {
            if (frame instanceof ActualizableConIdioma a) a.actualizarTextos();
        }
    }

    public void actualizarTextos() {
        setTitle(mensajes.get("app.titulo"));

        menuProducto.setText(mensajes.get("menu.producto"));
        menuCarrito .setText(mensajes.get("menu.carrito"));
        menuUsuario .setText(mensajes.get("menu.usuario"));
        menuIdioma  .setText(mensajes.get("menu.idiomas"));
        menuSesion  .setText(mensajes.get("menu.salir"));

        menuItemCrearProducto     .setText(mensajes.get("menu.producto.crear"));
        menuItemBuscarProducto    .setText(mensajes.get("menu.producto.buscar"));
        menuItemActualizarProducto.setText(mensajes.get("menu.producto.actualizar"));
        menuItemEliminarProducto  .setText(mensajes.get("menu.producto.eliminar"));

        menuItemCrearCarrito      .setText(mensajes.get("menu.carrito.crear"));
        menuItemEliminarCarrito   .setText(mensajes.get("menu.carrito.eliminar"));
        menuItemModificarCarrito  .setText(mensajes.get("menu.carrito.modificar"));
        menuItemListarCarritos    .setText(mensajes.get("menu.carrito.listar"));
        menuItemListarMisCarritos .setText(mensajes.get("menu.carrito.listarMis"));

        menuItemCrearUsuario      .setText(mensajes.get("menu.usuario.crear"));
        menuItemEliminarUsuario   .setText(mensajes.get("menu.usuario.eliminar"));
        menuItemModificarUsuario  .setText(mensajes.get("menu.usuario.modificar"));
        menuItemListarUsuarios    .setText(mensajes.get("menu.usuario.listar"));
        menuItemActualizarDatos   .setText(mensajes.get("menu.usuario.modificarMis"));

        menuItemCerrarSesion      .setText(mensajes.get("menu.salir.cerrar"));
        menuItemSalir             .setText(mensajes.get("menu.salir.salir"));

        menuItemIdiomaEspanol     .setText(mensajes.get("menu.idioma.es"));
        menuItemIdiomaIngles      .setText(mensajes.get("menu.idioma.en"));
        menuItemIdiomaFrances     .setText(mensajes.get("menu.idioma.fr"));
    }

    // ──────────────────── Roles ────────────────────
    public void deshabilitarMenusAdministrador() {
        menuProducto.setEnabled(false);
        menuItemListarCarritos.setEnabled(false);
        menuItemEliminarCarrito.setEnabled(false);
        menuItemModificarCarrito.setEnabled(false);
    }

    public void ocultarMenusAdministrador() {
        menuProducto.setVisible(false);
        menuItemListarCarritos.setVisible(false);
        menuItemEliminarCarrito.setVisible(false);
        menuItemModificarCarrito.setVisible(false);

        menuUsuario.setText(mensajes.get("menu.usuario"));
        menuUsuario.removeAll();
        menuUsuario.add(menuItemActualizarDatos);
        menuUsuario.setVisible(true);
    }

    // ──────────────────── Getters requeridos por Main ────────────────────
    public JMenuItem getMenuItemCrearUsuario()      { return menuItemCrearUsuario; }
    public JMenuItem getMenuItemEliminarUsuario()   { return menuItemEliminarUsuario; }
    public JMenuItem getMenuItemModificarUsuario()  { return menuItemModificarUsuario; }
    public JMenuItem getMenuItemListarUsuarios()    { return menuItemListarUsuarios; }
    public JMenuItem getMenuItemActualizarDatos()   { return menuItemActualizarDatos; }

    public JMenuItem getMenuItemCrearProducto()     { return menuItemCrearProducto; }
    public JMenuItem getMenuItemBuscarProducto()    { return menuItemBuscarProducto; }
    public JMenuItem getMenuItemActualizarProducto(){ return menuItemActualizarProducto; }
    public JMenuItem getMenuItemEliminarProducto()  { return menuItemEliminarProducto; }

    public JMenuItem getMenuItemCrearCarrito()      { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemEliminarCarrito()   { return menuItemEliminarCarrito; }
    public JMenuItem getMenuItemModificarCarrito()  { return menuItemModificarCarrito; }
    public JMenuItem getMenuItemListarCarritos()    { return menuItemListarCarritos; }
    public JMenuItem getMenuItemListarMisCarritos() { return menuItemListarMisCarritos; }

    public JMenuItem getMenuItemIdiomaEspanol()     { return menuItemIdiomaEspanol; }
    public JMenuItem getMenuItemIdiomaIngles()      { return menuItemIdiomaIngles; }
    public JMenuItem getMenuItemIdiomaFrances()     { return menuItemIdiomaFrances; }

    public JMenuItem getMenuItemSalir()             { return menuItemSalir; }
    public JMenuItem getMenuItemCerrarSesion()      { return menuItemCerrarSesion; }

    public JDesktopPane getjDesktopPane()           { return jDesktopPane; }

    // ──────────────────── Util ────────────────────
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
