package ec.edu.ups.controlador;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.modelo.servicio.CarritoService;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;

import java.util.List;
import java.util.stream.Collectors;

public class CarritoController {

    private CarritoService carritoService;
    private CarritoDAO carritoDAO;
    private Usuario usuarioActual;
    private ProductoDAO productoDAO;


    public CarritoController(CarritoService carritoService, ProductoDAO productoDAO,CarritoDAO carritoDAO, Usuario usuarioActual) {
        this.carritoService = carritoService;
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.usuarioActual = usuarioActual;
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

    // Guardar el carrito actual (temporal) como definitivo
    public void guardarCarrito(Carrito carrito) {
        carrito.setUsuario(usuarioActual);
        carritoDAO.guardar(carrito);
    }

    // Buscar un carrito por su código
    public Carrito buscarCarrito(int codigoCarrito) {
        return carritoDAO.buscarPorCodigo(codigoCarrito);
    }

    // Eliminar un carrito por su código
    public void eliminarCarrito(int codigoCarrito) {
        carritoDAO.eliminarPorCodigo(codigoCarrito);
    }

    // Listar todos los carritos (solo para administradores)
    public List<Carrito> listarTodosCarritos() {
        if (usuarioActual.getRol() == Rol.ADMINISTRADOR) {
            return carritoDAO.listarCarritos();
        }
        return null;
    }

    // Listar los carritos propios del usuario actual
    public List<Carrito> listarMisCarritos() {
        return carritoDAO.listarCarritos().stream()
                .filter(c -> c.getUsuario() != null && c.getUsuario().getUsername().equals(usuarioActual.getUsername()))
                .collect(Collectors.toList());
    }

    // Modificar cantidad de un producto en un carrito ya guardado
    public void modificarCantidad(Carrito carrito, int codigoProducto, int nuevaCantidad) {
        carrito.actualizarCantidadProducto(codigoProducto, nuevaCantidad);
    }
    public Producto buscarProductoPorCodigo(int codigo) {
        return productoDAO.buscarPorCodigo(codigo);
    }

}
