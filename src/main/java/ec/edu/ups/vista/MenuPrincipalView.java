package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.util.ActualizableConIdioma;

import javax.swing.*;

/**
 * Vista principal del sistema en forma de ventana JFrame con soporte para menú MDI.
 * Esta clase organiza los menús de productos, carritos, usuarios, sesión e idiomas,
 * permitiendo interacción dinámica e internacionalización de la interfaz.
 *
 * <p>La ventana es sensible al rol del usuario autenticado y ajusta sus menús según permisos.</p>
 *
 * @author Mateo
 * @version 1.0
 */
public class MenuPrincipalView extends JFrame {

    // --- Componentes de la interfaz gráfica ---
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

    private final MensajeInternacionalizacionHandler mensajes;
    private final CarritoController carritoController;

    /**
     * Constructor de la vista principal.
     *
     * @param mensajes            Manejador de internacionalización.
     * @param carritoController   Controlador de carritos.
     */
    public MenuPrincipalView(MensajeInternacionalizacionHandler mensajes, CarritoController carritoController) {
        this.mensajes = mensajes;
        this.carritoController = carritoController;
        initComponents();
        configurarIdiomaListeners();
    }

    /**
     * Inicializa los componentes visuales y los menús de la ventana principal.
     */
    private void initComponents() {
        setTitle("Sistema de Gestión - Carrito de Compras");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(jDesktopPane);

        // Organización de menús
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

        // Agregar menús a la barra
        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuBar.add(menuUsuario);
        menuBar.add(menuIdioma);
        menuBar.add(menuSesion);
        setJMenuBar(menuBar);

        actualizarTextos(mensajes);
    }

    /**
     * Configura los listeners de cambio de idioma para los ítems del menú correspondiente.
     */
    private void configurarIdiomaListeners() {
        menuItemIdiomaEspanol.addActionListener(e -> cambiarIdioma("es", "EC"));
        menuItemIdiomaIngles .addActionListener(e -> cambiarIdioma("en", "US"));
        menuItemIdiomaFrances.addActionListener(e -> cambiarIdioma("fr", "FR"));
    }

    /**
     * Cambia el idioma de la interfaz y actualiza todas las vistas internas que implementan la interfaz ActualizableConIdioma.
     *
     * @param lang    Código de idioma (por ejemplo, "es").
     * @param country Código de país (por ejemplo, "EC").
     */
    public void cambiarIdioma(String lang, String country) {
        mensajes.cambiarIdioma(lang, country);
        actualizarTextos(mensajes);
        for (JInternalFrame frame : jDesktopPane.getAllFrames()) {
            if (frame instanceof ActualizableConIdioma a) a.actualizarTextos(mensajes);
        }
    }

    /**
     * Actualiza los textos de los menús e ítems según el idioma actual cargado.
     *
     * @param mensajes Instancia del manejador de internacionalización.
     */
    public void actualizarTextos(MensajeInternacionalizacionHandler mensajes) {
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

    /**
     * Desactiva funcionalidades exclusivas del rol administrador cuando el usuario autenticado es de tipo USUARIO.
     */
    public void deshabilitarMenusAdministrador() {
        menuProducto.setEnabled(false);
        menuItemListarCarritos.setEnabled(false);
        menuItemEliminarCarrito.setEnabled(false);
        menuItemModificarCarrito.setEnabled(false);
    }

    /**
     * Oculta completamente los menús reservados para administradores, dejando visible solo la opción de actualizar datos personales.
     */
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

    // --- Getters públicos para controladores ---

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

    /**
     * Devuelve el escritorio MDI principal que contiene las ventanas internas.
     *
     * @return JDesktopPane del sistema.
     */
    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    /**
     * Muestra un mensaje emergente en la interfaz principal.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
