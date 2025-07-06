package ec.edu.ups.controlador;

import ec.edu.ups.modelo.servicio.CarritoServiceImpl;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.servicio.CarritoService;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;

import java.util.List;

public class CarritoController {

    private final CarritoService carritoService;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final UsuarioController usuarioController;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, UsuarioController usuarioController) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.usuarioController = usuarioController;
        this.carritoService = new CarritoServiceImpl();
    }

    // Agregar producto al carrito temporal
    public void agregarProducto(Producto producto, int cantidad) {
        carritoService.agregarProducto(producto, cantidad);
    }

    // Eliminar producto del carrito temporal
    public void eliminarProducto(int codigoProducto) {
        carritoService.eliminarProducto(codigoProducto);
    }

    // Vaciar carrito temporal
    public void vaciarCarrito() {
        carritoService.vaciarCarrito();
    }

    // Calcular total (subtotal + IVA)
    public double calcularTotal() {
        return carritoService.calcularTotal();
    }

    // Obtener ítems del carrito temporal
    public List<ItemCarrito> obtenerItems() {
        return carritoService.obtenerItems();
    }

    // Verificar si el carrito está vacío
    public boolean estaVacio() {
        return carritoService.estaVacio();
    }

    // Guardar el carrito actual como definitivo
    public void guardarCarrito(Carrito carrito) {
        // Agregamos los productos del carrito temporal al carrito real
        for (ItemCarrito item : carritoService.obtenerItems()) {
            carrito.agregarProducto(item.getProducto(), item.getCantidad());
        }

        // Asignamos el usuario autenticado
        carrito.setUsuario(usuarioController.getUsuarioAutenticado());

        // Guardamos el carrito en el DAO
        carritoDAO.guardar(carrito);

        // Limpieza del carrito temporal
        carritoService.vaciarCarrito();

        // Trazas para depuración
        System.out.println(">>> Carrito guardado con código: " + carrito.getCodigo());
        System.out.println(">>> Total productos: " + carrito.obtenerItems().size());
        System.out.println(">>> Usuario: " + carrito.getUsuario().getUsername());
    }

    public void imprimirTodosCarritosDebug() {
        List<Carrito> lista = carritoDAO.listarCarritos();
        System.out.println(">>> LISTA DE CARRITOS");
        for (Carrito c : lista) {
            System.out.println("- Código: " + c.getCodigo() + " | Usuario: " +
                    (c.getUsuario() != null ? c.getUsuario().getUsername() : "null") +
                    " | Productos: " + c.obtenerItems().size());
        }
    }

    // Buscar un carrito por código
    public Carrito buscarCarrito(int codigoCarrito) {
        return carritoDAO.buscarPorCodigo(codigoCarrito);
    }

    // Eliminar un carrito
    public void eliminarCarrito(int codigoCarrito) {
        carritoDAO.eliminarPorCodigo(codigoCarrito);
    }

    // Listar todos los carritos (solo admin)
    public List<Carrito> listarTodosCarritos() {
        Usuario usuario = usuarioController.getUsuarioAutenticado();
        System.out.println(">>> Usuario autenticado: " + (usuario != null ? usuario.getUsername() : "null"));
        if (usuario != null && usuario.getRol() == Rol.ADMINISTRADOR) {
            System.out.println(">>> Retornando todos los carritos...");
            return carritoDAO.listarCarritos();
        }
        System.out.println(">>> Usuario no autorizado para ver todos los carritos.");
        return null;
    }

    // Listar carritos del usuario actual
    public List<Carrito> listarMisCarritos() {
        Usuario usuario = usuarioController.getUsuarioAutenticado();
        if (usuario != null && usuario.getRol() == Rol.ADMINISTRADOR) {
            return carritoDAO.listarCarritos();
        }
        return null;
    }

    public List<Carrito> listarCarritosSinFiltro() {
        return carritoDAO.listarCarritos();
    }


    // Listar carritos de un usuario específico
    public List<Carrito> listarMisCarritos(Usuario usuario) {
        return carritoDAO.listarCarritos().stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getUsername().equals(usuario.getUsername()))
                .toList();
    }

    // Buscar producto por código
    public Producto buscarProductoPorCodigo(int codigo) {
        return productoDAO.buscarPorCodigo(codigo);
    }

    // Modificar cantidad de un producto en un carrito ya guardado
    public void modificarCantidad(Carrito carrito, int codigoProducto, int nuevaCantidad) {
        carrito.actualizarCantidadProducto(codigoProducto, nuevaCantidad);
    }
}
