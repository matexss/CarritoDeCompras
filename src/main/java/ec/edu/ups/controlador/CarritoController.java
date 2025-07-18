package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.servicio.CarritoService;
import ec.edu.ups.modelo.servicio.CarritoServiceImpl;

import java.util.List;

/**
 * Controlador que gestiona la lógica relacionada con el carrito de compras.
 * Coordina la interacción entre la vista, el modelo y los servicios DAO.
 *
 * Mateo Morejon
 * @version 1.0
 */
public class CarritoController {

    private final CarritoService carritoService;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final UsuarioController usuarioController;

    /**
     * Constructor del controlador de carrito.
     *
     * @param carritoDAO DAO para persistencia de carritos.
     * @param productoDAO DAO para acceso a productos.
     * @param usuarioController Controlador de usuario autenticado.
     */
    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, UsuarioController usuarioController) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.usuarioController = usuarioController;
        this.carritoService = new CarritoServiceImpl();
    }

    /**
     * Agrega un producto al carrito actual.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        carritoService.agregarProducto(producto, cantidad);
    }

    /**
     * Elimina un producto del carrito actual.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        carritoService.eliminarProducto(codigoProducto);
    }

    /**
     * Vacía todos los productos del carrito actual.
     */
    public void vaciarCarrito() {
        carritoService.vaciarCarrito();
    }

    /**
     * Calcula el total del carrito con IVA.
     *
     * @return Total del carrito.
     */
    public double calcularTotal() {
        return carritoService.calcularTotal();
    }

    /**
     * Retorna la lista de ítems actuales del carrito.
     *
     * @return Lista de {@link ItemCarrito}.
     */
    public List<ItemCarrito> obtenerItems() {
        return carritoService.obtenerItems();
    }

    /**
     * Verifica si el carrito está vacío.
     *
     * @return true si está vacío, false en caso contrario.
     */
    public boolean estaVacio() {
        return carritoService.estaVacio();
    }

    /**
     * Guarda el carrito actual en la base de datos y lo asocia al usuario autenticado.
     *
     * @param carrito Objeto carrito a guardar.
     */
    public void guardarCarrito(Carrito carrito) {
        for (ItemCarrito item : carritoService.obtenerItems()) {
            carrito.agregarProducto(item.getProducto(), item.getCantidad());
        }

        Usuario usuario = usuarioController.getUsuarioAutenticado();
        carrito.setUsuario(usuario);

        carritoDAO.guardar(carrito);
        System.out.println(">>> DAO: Carrito guardado con código " + carrito.getCodigo());

        carritoService.vaciarCarrito();
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto buscarProductoPorCodigo(int codigo) {
        return productoDAO.buscarPorCodigo(codigo);
    }

    /**
     * Busca un carrito por su código.
     *
     * @param codigoCarrito Código del carrito.
     * @return Carrito encontrado o null si no existe.
     */
    public Carrito buscarCarrito(int codigoCarrito) {
        return carritoDAO.buscarPorCodigo(codigoCarrito);
    }

    /**
     * Lista todos los carritos si el usuario autenticado es administrador.
     *
     * @return Lista de carritos o null si el usuario no es administrador.
     */
    public List<Carrito> listarTodosCarritos() {
        Usuario usuario = usuarioController.getUsuarioAutenticado();
        if (usuario != null && Rol.ADMINISTRADOR.equals(usuario.getRol())) {
            return carritoDAO.listarTodos();
        }
        return null;
    }

    /**
     * Lista los carritos del usuario autenticado si su rol es USUARIO.
     *
     * @return Lista de carritos del usuario autenticado.
     */
    public List<Carrito> listarMisCarritos() {
        Usuario usuario = usuarioController.getUsuarioAutenticado();
        if (usuario != null && Rol.USUARIO.equals(usuario.getRol())) {
            return carritoDAO.listarTodos().stream()
                    .filter(c -> c.getUsuario() != null &&
                            c.getUsuario().getUsername().equals(usuario.getUsername()))
                    .toList();
        }
        return List.of();
    }

    /**
     * Lista todos los carritos sin aplicar filtro de usuario o rol.
     *
     * @return Lista de todos los carritos.
     */
    public List<Carrito> listarCarritosSinFiltro() {
        return carritoDAO.listarTodos();
    }

    /**
     * Elimina un carrito por su código.
     *
     * @param codigo Código del carrito.
     */
    public void eliminarCarrito(int codigo) {
        carritoDAO.eliminarPorCodigo(codigo);
    }

    /**
     * Modifica la cantidad de un producto dentro de un carrito existente.
     *
     * @param carrito Carrito a modificar.
     * @param codigoProducto Código del producto.
     * @param nuevaCantidad Nueva cantidad.
     */
    public void modificarCantidad(Carrito carrito, int codigoProducto, int nuevaCantidad) {
        if (carrito == null) return;

        carrito.actualizarCantidadProducto(codigoProducto, nuevaCantidad);
        System.out.println(">>> Cantidad modificada en el carrito " + carrito.getCodigo() + ": Producto " + codigoProducto + " → " + nuevaCantidad);
    }

    /**
     * Imprime todos los carritos en consola para propósitos de depuración.
     */
    public void imprimirTodosCarritosDebug() {
        System.out.println(">>> LISTA DE CARRITOS:");
        List<Carrito> carritos = carritoDAO.listarTodos();
        for (Carrito c : carritos) {
            String usuario = (c.getUsuario() != null) ? c.getUsuario().getUsername() : "desconocido";
            System.out.println("- Carrito " + c.getCodigo() + " de " + usuario + " → " + c.obtenerItems().size() + " productos.");
        }
    }
}
