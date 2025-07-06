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

    public void agregarProducto(Producto producto, int cantidad) {
        carritoService.agregarProducto(producto, cantidad);
    }

    public void eliminarProducto(int codigoProducto) {
        carritoService.eliminarProducto(codigoProducto);
    }

    public void vaciarCarrito() {
        carritoService.vaciarCarrito();
    }

    public double calcularTotal() {
        return carritoService.calcularTotal();
    }

    public List<ItemCarrito> obtenerItems() {
        return carritoService.obtenerItems();
    }

    public boolean estaVacio() {
        return carritoService.estaVacio();
    }

    public void guardarCarrito(Carrito carrito) {
        for (ItemCarrito item : carritoService.obtenerItems()) {
            carrito.agregarProducto(item.getProducto(), item.getCantidad());
        }
        carrito.setUsuario(usuarioController.getUsuarioAutenticado()); // ⚠️ CRUCIAL
        carritoDAO.crear(carrito);
        System.out.println(">>> DAO: Carrito guardado con código " + carrito.getCodigo());
        carritoService.vaciarCarrito();
    }


    public Producto buscarProductoPorCodigo(int codigo) {
        return productoDAO.buscarPorCodigo(codigo);
    }

    public Carrito buscarCarrito(int codigoCarrito) {
        return carritoDAO.buscarPorCodigo(codigoCarrito);
    }

    public List<Carrito> listarTodosCarritos() {
        Usuario usuario = usuarioController.getUsuarioAutenticado();
        if (usuario != null && usuario.getRol() == Rol.ADMINISTRADOR) {
            return carritoDAO.listarTodos();
        }
        return null;
    }

    public List<Carrito> listarMisCarritos() {
        Usuario usuario = usuarioController.getUsuarioAutenticado();
        if (usuario != null && usuario.getRol() == Rol.USUARIO) {
            return carritoDAO.listarTodos().stream()
                    .filter(c -> c.getUsuario() != null &&
                            c.getUsuario().getUsername().equals(usuario.getUsername()))
                    .toList();
        }
        return List.of(); // devuelve lista vacía, NO null

    }

    public List<Carrito> listarCarritosSinFiltro() {
        return carritoDAO.listarTodos();
    }

    public void eliminarCarrito(int codigo) {
        carritoDAO.eliminarPorCodigo(codigo);
    }

    public void modificarCantidad(Carrito carrito, int codigoProducto, int nuevaCantidad) {
        for (ItemCarrito item : carrito.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
        System.out.println(">>> Producto con código " + codigoProducto + " no encontrado en el carrito.");
    }


    public void imprimirTodosCarritosDebug() {
        System.out.println(">>> LISTA DE CARRITOS:");
        List<Carrito> carritos = carritoDAO.listarTodos();
        for (Carrito c : carritos) {
            String usuario = (c.getUsuario() != null) ? c.getUsuario().getUsername() : "desconocido";
            System.out.println("- Carrito " + c.getCodigo() + " de " + usuario + " → " + c.obtenerItems().size() + " productos.");
        }
    }
}
